package com.girish.premiumapp.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girish.premiumapp.domain.model.TaskEntity
import com.girish.premiumapp.domain.model.TaskPriority
import com.girish.premiumapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        isHabit: Boolean = false
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
                    isHabit = isHabit
                )
            )
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun toggleTaskCompletion(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            val task = _tasks.value.find { it.id == taskId }
            if (task != null && task.isHabit && isCompleted) {
                // It's a habit being completed. Mark this one complete, increment streak, and create next.
                val updatedTask = task.copy(isCompleted = true, habitStreak = task.habitStreak + 1)
                repository.updateTask(updatedTask)
                
                // Create a new uncompleted task for tomorrow
                val tomorrow = java.util.Calendar.getInstance().apply {
                    task.dueDate?.let { timeInMillis = it }
                    add(java.util.Calendar.DAY_OF_YEAR, 1)
                }.timeInMillis
                
                repository.insertTask(
                    task.copy(
                        id = 0, // new ID
                        isCompleted = false,
                        dueDate = tomorrow,
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
}
