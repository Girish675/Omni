package com.girish.premiumapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class RecurrenceRule {
    NONE, DAILY, WEEKLY, MONTHLY, YEARLY
}

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val isAllDay: Boolean,
    val recurrenceRule: RecurrenceRule = RecurrenceRule.NONE
)
