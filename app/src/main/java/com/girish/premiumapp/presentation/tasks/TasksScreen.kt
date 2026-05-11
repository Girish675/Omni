package com.girish.premiumapp.presentation.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.girish.premiumapp.domain.model.TaskEntity
import com.girish.premiumapp.domain.model.TaskPriority
import com.girish.premiumapp.presentation.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    onBack: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    val pending = tasks.count { !it.isCompleted }
    val completed = tasks.count { it.isCompleted }

    var isBoardView by remember { mutableStateOf(false) }
    var selectedTaskIds by remember { mutableStateOf(setOf<Int>()) }
    val isSelectionMode = selectedTaskIds.isNotEmpty()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (isSelectionMode) {
                TopAppBar(
                    title = { Text("${selectedTaskIds.size} selected") },
                    navigationIcon = {
                        IconButton(onClick = { selectedTaskIds = emptySet() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Cancel selection")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            selectedTaskIds.forEach { viewModel.deleteTask(it) }
                            selectedTaskIds = emptySet()
                        }) {
                            Icon(Icons.Default.Delete, "Delete Selected", tint = MaterialTheme.colorScheme.error)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                )
            } else {
                TopAppBar(
                    title = {
                        Column {
                            Text("Tasks")
                            if (tasks.isNotEmpty()) {
                                Text("$pending pending • $completed done", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
                    },
                    actions = {
                        IconButton(onClick = { isBoardView = !isBoardView }) {
                            Icon(if (isBoardView) Icons.AutoMirrored.Filled.List else Icons.Default.Dashboard, contentDescription = "Toggle View")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No tasks yet. Add one!", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else if (isBoardView) {
            val priorities = TaskPriority.entries
            LazyRow(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(priorities) { priority ->
                    val priorityTasks = tasks.filter { it.priority == priority && !it.isCompleted }
                    Column(modifier = Modifier.width(300.dp)) {
                        Text(
                            text = "${priority.name} (${priorityTasks.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(priorityTasks, key = { it.id }) { task ->
                                TaskItem(
                                    task = task,
                                    onToggle = { isChecked -> viewModel.toggleTaskCompletion(task.id, isChecked) },
                                    onDelete = {
                                        viewModel.deleteTask(task.id)
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar(
                                                message = "Task deleted",
                                                actionLabel = "Undo",
                                                duration = SnackbarDuration.Short
                                            )
                                            if (result == SnackbarResult.ActionPerformed) {
                                                viewModel.undoDelete()
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    val isSelected = selectedTaskIds.contains(task.id)
                    TaskItem(
                        task = task,
                        isSelected = isSelected,
                        onToggle = { isChecked ->
                            if (isSelectionMode) {
                                selectedTaskIds = if (isSelected) selectedTaskIds - task.id else selectedTaskIds + task.id
                            } else {
                                viewModel.toggleTaskCompletion(task.id, isChecked)
                            }
                        },
                        onLongPress = { selectedTaskIds = selectedTaskIds + task.id },
                        onDelete = {
                            viewModel.deleteTask(task.id)
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Task deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Short
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.undoDelete()
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, desc, priority, dueDate, subtasks, tags, _, habit ->
                viewModel.addTask(title, desc, priority, dueDate, subtasks, tags, 0, habit)
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    task: TaskEntity,
    isSelected: Boolean = false,
    onToggle: (Boolean) -> Unit,
    onLongPress: () -> Unit = {},
    onDelete: () -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) { isDismissed = true; true } else false
        }
    )

    LaunchedEffect(isDismissed) {
        if (isDismissed) { delay(300); onDelete() }
    }

    val priorityColor = when (task.priority) {
        TaskPriority.LOW -> PriorityLow
        TaskPriority.MEDIUM -> PriorityMedium
        TaskPriority.HIGH -> PriorityHigh
        TaskPriority.URGENT -> PriorityUrgent
    }

    val isOverdue = task.dueDate != null && task.dueDate < System.currentTimeMillis() && !task.isCompleted

    AnimatedVisibility(
        visible = !isDismissed,
        exit = shrinkVertically() + fadeOut(animationSpec = tween(300))
    ) {
        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                Box(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.error, RoundedCornerShape(12.dp)).padding(end = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.onError)
                }
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = { onToggle(!task.isCompleted) },
                        onLongClick = onLongPress
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Priority indicator strip
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(priorityColor)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = onToggle,
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium,
                            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                            color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                        )
                        if (task.description.isNotBlank()) {
                            Text(
                                text = task.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                maxLines = 2
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Priority badge
                            AssistChip(
                                onClick = {},
                                label = { Text(task.priority.name, style = MaterialTheme.typography.labelSmall) },
                                modifier = Modifier.height(24.dp),
                                colors = AssistChipDefaults.assistChipColors(containerColor = priorityColor.copy(alpha = 0.2f), labelColor = priorityColor)
                            )
                            // Due date
                            task.dueDate?.let { due ->
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(due)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isOverdue) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                    fontWeight = if (isOverdue) FontWeight.Bold else FontWeight.Normal
                                )
                                if (isOverdue) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("OVERDUE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Habit / Tags / Subtasks row
                        if (task.isHabit || task.tags.isNotBlank() || task.subtasks.isNotBlank()) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                                if (task.isHabit) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Habit", modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                if (task.tags.isNotBlank()) {
                                    val tagList = task.tags.split(",")
                                    Text(
                                        text = tagList.joinToString(" ") { "#${it.trim()}" },
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                if (task.subtasks.isNotBlank()) {
                                    val subtaskList = task.subtasks.split(",")
                                    Text(
                                        text = "${subtaskList.size} subtasks",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, TaskPriority, Long?, String, String, Int, Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(TaskPriority.MEDIUM) }
    var priorityExpanded by remember { mutableStateOf(false) }
    var subtasks by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var isHabit by remember { mutableStateOf(false) }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                // Priority selector
                ExposedDropdownMenuBox(expanded = priorityExpanded, onExpandedChange = { priorityExpanded = it }) {
                    OutlinedTextField(
                        value = priority.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Priority") },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = priorityExpanded, onDismissRequest = { priorityExpanded = false }) {
                        TaskPriority.entries.forEach { p ->
                            val color = when (p) {
                                TaskPriority.LOW -> PriorityLow
                                TaskPriority.MEDIUM -> PriorityMedium
                                TaskPriority.HIGH -> PriorityHigh
                                TaskPriority.URGENT -> PriorityUrgent
                            }
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(color))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(p.name)
                                    }
                                },
                                onClick = { priority = p; priorityExpanded = false }
                            )
                        }
                    }
                }

                // Due date picker
                OutlinedTextField(
                    value = if (datePickerState.selectedDateMillis != null) {
                        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(datePickerState.selectedDateMillis!!))
                    } else "No due date",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Due Date (Optional)") },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = subtasks,
                    onValueChange = { subtasks = it },
                    label = { Text("Subtasks (comma separated)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("Tags (comma separated)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isHabit, onCheckedChange = { isHabit = it })
                    Text("Daily Habit")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank()) onConfirm(title, description, priority, datePickerState.selectedDateMillis, subtasks, tags, 0, isHabit) },
                enabled = title.isNotBlank()
            ) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = { TextButton(onClick = { showDatePicker = false }) { Text("OK") } },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } }
        ) { DatePicker(state = datePickerState) }
    }
}
