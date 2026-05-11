package com.girish.premiumapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.girish.premiumapp.domain.model.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY startTime ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEvent(eventId: Int)

    @Query("SELECT * FROM events WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    suspend fun searchEvents(query: String): List<EventEntity>

    @Query("SELECT * FROM events ORDER BY startTime ASC")
    fun getAllEventsList(): List<EventEntity>

    @Query("SELECT COUNT(*) FROM events WHERE startTime >= :startOfDay AND startTime < :endOfDay")
    suspend fun getTodayEventCount(startOfDay: Long, endOfDay: Long): Int
}
