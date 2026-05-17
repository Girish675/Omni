package com.girish.premiumapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.girish.premiumapp.domain.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY isPinned DESC, lastModified DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNote(noteId: Int)

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    suspend fun searchNotes(query: String): List<NoteEntity>

    @Query("SELECT * FROM notes ORDER BY lastModified DESC")
    fun getAllNotesList(): List<NoteEntity>

    @Query("SELECT COUNT(*) FROM notes")
    suspend fun getNoteCount(): Int
}
