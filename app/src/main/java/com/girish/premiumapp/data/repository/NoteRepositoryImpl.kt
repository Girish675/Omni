package com.girish.premiumapp.data.repository

import com.girish.premiumapp.data.local.NoteDao
import com.girish.premiumapp.domain.model.NoteEntity
import com.girish.premiumapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<NoteEntity>> = dao.getAllNotes()
    override suspend fun insertNote(note: NoteEntity) = dao.insertNote(note)
    override suspend fun deleteNote(noteId: Int) = dao.deleteNote(noteId)
    override suspend fun searchNotes(query: String): List<NoteEntity> = dao.searchNotes(query)
    override suspend fun getNoteCount(): Int = dao.getNoteCount()
}
