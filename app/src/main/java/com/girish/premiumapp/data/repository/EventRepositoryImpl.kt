package com.girish.premiumapp.data.repository

import com.girish.premiumapp.data.local.EventDao
import com.girish.premiumapp.domain.model.EventEntity
import com.girish.premiumapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val dao: EventDao
) : EventRepository {
    override fun getAllEvents(): Flow<List<EventEntity>> = dao.getAllEvents()
    override suspend fun insertEvent(event: EventEntity) = dao.insertEvent(event)
    override suspend fun updateEvent(event: EventEntity) = dao.updateEvent(event)
    override suspend fun deleteEvent(eventId: Int) = dao.deleteEvent(eventId)
    override suspend fun searchEvents(query: String): List<EventEntity> = dao.searchEvents(query)
    override suspend fun getTodayEventCount(startOfDay: Long, endOfDay: Long): Int = dao.getTodayEventCount(startOfDay, endOfDay)
}
