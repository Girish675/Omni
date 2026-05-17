package com.girish.premiumapp.data.repository

import com.girish.premiumapp.data.local.TaskDao
import com.girish.premiumapp.domain.model.TaskEntity
import com.girish.premiumapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao
) : TaskRepository {
    override fun getAllTasks(): Flow<List<TaskEntity>> = dao.getAllTasks()
    override suspend fun insertTask(task: TaskEntity) = dao.insertTask(task)
    override suspend fun updateTask(task: TaskEntity) = dao.updateTask(task)
    override suspend fun deleteTask(taskId: Int) = dao.deleteTask(taskId)
    override suspend fun updateTaskStatus(taskId: Int, isCompleted: Boolean) = dao.updateTaskStatus(taskId, isCompleted)
    override suspend fun searchTasks(query: String): List<TaskEntity> = dao.searchTasks(query)
    override suspend fun getPendingTaskCount(): Int = dao.getPendingTaskCount()
    override suspend fun getTotalTaskCount(): Int = dao.getTotalTaskCount()
}
