package com.girish.premiumapp.domain.repository

import com.girish.premiumapp.domain.model.EventEntity
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getAllEvents(): Flow<List<EventEntity>>
    suspend fun insertEvent(event: EventEntity)
    suspend fun updateEvent(event: EventEntity)
    suspend fun deleteEvent(eventId: Int)
    suspend fun searchEvents(query: String): List<EventEntity>
    suspend fun getTodayEventCount(startOfDay: Long, endOfDay: Long): Int
}
