package com.girish.premiumapp.domain.repository

import com.girish.premiumapp.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(taskId: Int)
    suspend fun updateTaskStatus(taskId: Int, isCompleted: Boolean)
    suspend fun searchTasks(query: String): List<TaskEntity>
    suspend fun getPendingTaskCount(): Int
    suspend fun getTotalTaskCount(): Int
}
