package com.girish.premiumapp.domain.repository

import com.girish.premiumapp.domain.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity)
    suspend fun deleteNote(noteId: Int)
    suspend fun searchNotes(query: String): List<NoteEntity>
    suspend fun getNoteCount(): Int
}
