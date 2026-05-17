package com.girish.premiumapp.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girish.premiumapp.domain.model.TaskEntity
import com.girish.premiumapp.domain.model.TaskPriority
import com.girish.premiumapp.domain.model.TaskRecurrence
import com.girish.premiumapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class ParsedTaskInput(
    val title: String,
    val description: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: Long? = null,
    val tags: String = "",
    val estimatedTimeMinutes: Int = 0,
    val isHabit: Boolean = false,
    val recurrenceRule: TaskRecurrence = TaskRecurrence.NONE,
    val reminderTime: Long? = null
)

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks.asStateFlow()

    private var recentlyDeletedTask: TaskEntity? = null

    init {
        getTasks()
    }

    private fun getTasks() {
        repository.getAllTasks().onEach { taskList ->
            _tasks.value = taskList
        }.launchIn(viewModelScope)
    }

    fun addTask(
        title: String,
        description: String,
        priority: TaskPriority = TaskPriority.MEDIUM,
        dueDate: Long? = null,
        subtasks: String = "",
        tags: String = "",
        estimatedTimeMinutes: Int = 0,
        isHabit: Boolean = false,
        recurrenceRule: TaskRecurrence = TaskRecurrence.NONE,
        reminderTime: Long? = null
    ) {
        viewModelScope.launch {
            repository.insertTask(
                TaskEntity(
                    title = title,
                    description = description,
                    priority = priority,
                    dueDate = dueDate,
                    subtasks = subtasks,
                    tags = tags,
                    estimatedTimeMinutes = estimatedTimeMinutes,
                    isHabit = isHabit,
                    recurrenceRule = recurrenceRule,
                    reminderTime = reminderTime
                )
            )
        }
    }

    fun addTaskFromNaturalLanguage(input: String) {
        val parsed = parseNaturalLanguageTask(input)
        if (parsed.title.isBlank()) return
        addTask(
            title = parsed.title,
            description = parsed.description,
            priority = parsed.priority,
            dueDate = parsed.dueDate,
            tags = parsed.tags,
            estimatedTimeMinutes = parsed.estimatedTimeMinutes,
            isHabit = parsed.isHabit,
            recurrenceRule = parsed.recurrenceRule,
            reminderTime = parsed.reminderTime
        )
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun toggleTaskCompletion(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            val task = _tasks.value.find { it.id == taskId }
            if (task != null && isCompleted && (task.isHabit || task.recurrenceRule != TaskRecurrence.NONE)) {
                val updatedTask = task.copy(
                    isCompleted = true,
                    habitStreak = if (task.isHabit) task.habitStreak + 1 else task.habitStreak
                )
                repository.updateTask(updatedTask)

                val nextDueDate = Calendar.getInstance().apply {
                    task.dueDate?.let { timeInMillis = it }
                    when (task.recurrenceRule) {
                        TaskRecurrence.DAILY, TaskRecurrence.NONE -> add(Calendar.DAY_OF_YEAR, 1)
                        TaskRecurrence.WEEKLY -> add(Calendar.WEEK_OF_YEAR, 1)
                        TaskRecurrence.MONTHLY -> add(Calendar.MONTH, 1)
                        TaskRecurrence.YEARLY -> add(Calendar.YEAR, 1)
                    }
                }.timeInMillis

                repository.insertTask(
                    task.copy(
                        id = 0,
                        isCompleted = false,
                        dueDate = nextDueDate,
                        reminderTime = nextDueDate,
                        habitStreak = updatedTask.habitStreak,
                        createdAt = System.currentTimeMillis()
                    )
                )
            } else {
                repository.updateTaskStatus(taskId, isCompleted)
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            recentlyDeletedTask = _tasks.value.find { it.id == taskId }
            repository.deleteTask(taskId)
        }
    }

    fun undoDelete() {
        recentlyDeletedTask?.let { task ->
            viewModelScope.launch {
                // Insert as new, or reuse ID if repository allows. 
                // Since room uses autoGenerate, inserting a non-zero ID will preserve it if it's not present.
                repository.insertTask(task)
                recentlyDeletedTask = null
            }
        }
    }

    fun previewNaturalLanguageTask(input: String): ParsedTaskInput = parseNaturalLanguageTask(input)

    private fun parseNaturalLanguageTask(input: String): ParsedTaskInput {
        var working = input.trim()
        if (working.isBlank()) return ParsedTaskInput(title = "")

        val tags = Regex("""#([\w-]+)""")
            .findAll(working)
            .map { it.groupValues[1] }
            .distinct()
            .joinToString(",")
        working = working.replace(Regex("""#([\w-]+)"""), " ")

        val lower = working.lowercase()
        val priority = when {
            Regex("""\b(urgent|asap|critical)\b""").containsMatchIn(lower) -> TaskPriority.URGENT
            Regex("""\b(high priority|important)\b""").containsMatchIn(lower) -> TaskPriority.HIGH
            Regex("""\b(low priority|whenever)\b""").containsMatchIn(lower) -> TaskPriority.LOW
            else -> TaskPriority.MEDIUM
        }
        working = working
            .replace(Regex("""\b(urgent|asap|critical|high priority|important|low priority|whenever)\b""", RegexOption.IGNORE_CASE), " ")

        val recurrence = when {
            Regex("""\bevery\s+day\b|\bdaily\b""").containsMatchIn(lower) -> TaskRecurrence.DAILY
            Regex("""\bevery\s+(mon|tue|wed|thu|fri|sat|sun|monday|tuesday|wednesday|thursday|friday|saturday|sunday|week)\b|\bweekly\b""").containsMatchIn(lower) -> TaskRecurrence.WEEKLY
            Regex("""\bevery\s+month\b|\bmonthly\b|\bon the \d{1,2}(st|nd|rd|th)?\b""").containsMatchIn(lower) -> TaskRecurrence.MONTHLY
            Regex("""\bevery\s+year\b|\byearly\b|\bannually\b""").containsMatchIn(lower) -> TaskRecurrence.YEARLY
            else -> TaskRecurrence.NONE
        }

        val dueDate = parseDueDate(lower)
        val reminderTime = dueDate
        val estimatedMinutes = parseEstimatedMinutes(lower)
        val isHabit = recurrence == TaskRecurrence.DAILY || Regex("""\bhabit\b""").containsMatchIn(lower)

        working = working
            .replace(Regex("""\bevery\s+(day|week|month|year|mon|tue|wed|thu|fri|sat|sun|monday|tuesday|wednesday|thursday|friday|saturday|sunday)\b""", RegexOption.IGNORE_CASE), " ")
            .replace(Regex("""\b(daily|weekly|monthly|yearly|annually|habit)\b""", RegexOption.IGNORE_CASE), " ")
            .replace(Regex("""\b(today|tomorrow|next week|next month)\b""", RegexOption.IGNORE_CASE), " ")
            .replace(Regex("""\bon the \d{1,2}(st|nd|rd|th)?\b""", RegexOption.IGNORE_CASE), " ")
            .replace(Regex("""\bat\s+\d{1,2}(:\d{2})?\s*(am|pm)?\b""", RegexOption.IGNORE_CASE), " ")
            .replace(Regex("""\b(in|for)\s+\d+\s*(m|min|mins|minutes|h|hr|hrs|hours)\b""", RegexOption.IGNORE_CASE), " ")
            .replace(Regex("""\s+"""), " ")
            .trim(' ', ',', '-', '.')

        return ParsedTaskInput(
            title = working.ifBlank { input.trim() },
            priority = priority,
            dueDate = dueDate,
            tags = tags,
            estimatedTimeMinutes = estimatedMinutes,
            isHabit = isHabit,
            recurrenceRule = recurrence,
            reminderTime = reminderTime
        )
    }

    private fun parseDueDate(lower: String): Long? {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        when {
            lower.contains("tomorrow") -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            lower.contains("next week") -> calendar.add(Calendar.DAY_OF_YEAR, 7)
            lower.contains("next month") -> calendar.add(Calendar.MONTH, 1)
            lower.contains("today") -> Unit
            else -> {
                val weekday = listOf(
                    "sunday" to Calendar.SUNDAY,
                    "monday" to Calendar.MONDAY,
                    "tuesday" to Calendar.TUESDAY,
                    "wednesday" to Calendar.WEDNESDAY,
                    "thursday" to Calendar.THURSDAY,
                    "friday" to Calendar.FRIDAY,
                    "saturday" to Calendar.SATURDAY
                ).firstOrNull { lower.contains(it.first) || lower.contains(it.first.take(3)) }

                val monthlyDay = Regex("""on the (\d{1,2})(st|nd|rd|th)?""").find(lower)
                when {
                    weekday != null -> {
                        val today = calendar.get(Calendar.DAY_OF_WEEK)
                        var delta = (weekday.second - today + 7) % 7
                        if (delta == 0) delta = 7
                        calendar.add(Calendar.DAY_OF_YEAR, delta)
                    }
                    monthlyDay != null -> {
                        val day = monthlyDay.groupValues[1].toIntOrNull()?.coerceIn(1, 28) ?: return null
                        calendar.set(Calendar.DAY_OF_MONTH, day)
                        if (calendar.timeInMillis < System.currentTimeMillis()) {
                            calendar.add(Calendar.MONTH, 1)
                        }
                    }
                    else -> return null
                }
            }
        }

        val time = Regex("""at\s+(\d{1,2})(?::(\d{2}))?\s*(am|pm)?""").find(lower)
        if (time != null) {
            var hour = time.groupValues[1].toIntOrNull() ?: 9
            val minute = time.groupValues[2].toIntOrNull() ?: 0
            val meridian = time.groupValues[3]
            if (meridian == "pm" && hour < 12) hour += 12
            if (meridian == "am" && hour == 12) hour = 0
            calendar.set(Calendar.HOUR_OF_DAY, hour.coerceIn(0, 23))
            calendar.set(Calendar.MINUTE, minute.coerceIn(0, 59))
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 9)
            calendar.set(Calendar.MINUTE, 0)
        }

        return calendar.timeInMillis
    }

    private fun parseEstimatedMinutes(lower: String): Int {
        val match = Regex("""\b(?:in|for)\s+(\d+)\s*(m|min|mins|minutes|h|hr|hrs|hours)\b""").find(lower) ?: return 0
        val value = match.groupValues[1].toIntOrNull() ?: return 0
        return if (match.groupValues[2].startsWith("h")) value * 60 else value
    }
}
