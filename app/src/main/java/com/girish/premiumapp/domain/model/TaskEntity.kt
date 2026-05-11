package com.girish.premiumapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TaskPriority {
    LOW, MEDIUM, HIGH, URGENT
}

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: Long? = null,
    val subtasks: String = "", // JSON or '|' separated
    val tags: String = "",
    val estimatedTimeMinutes: Int = 0,
    val isHabit: Boolean = false,
    val habitStreak: Int = 0
)
