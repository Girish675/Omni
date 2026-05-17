package com.girish.premiumapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val lastModified: Long = System.currentTimeMillis(),
    val color: Int = 0,
    val isPinned: Boolean = false,
    val tags: String = ""
)
