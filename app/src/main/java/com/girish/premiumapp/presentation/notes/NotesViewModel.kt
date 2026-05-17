package com.girish.premiumapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girish.premiumapp.domain.model.NoteEntity
import com.girish.premiumapp.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<NoteEntity>>(emptyList())
    val searchResults: StateFlow<List<NoteEntity>> = _searchResults.asStateFlow()

    init {
        getNotes()
    }

    private fun getNotes() {
        repository.getAllNotes().onEach { list ->
            _notes.value = list
        }.launchIn(viewModelScope)
    }

    fun saveNote(id: Int? = null, title: String, content: String, color: Int = 0, isPinned: Boolean = false, tags: String = "") {
        viewModelScope.launch {
            repository.insertNote(
                NoteEntity(
                    id = id ?: 0,
                    title = title,
                    content = content,
                    lastModified = System.currentTimeMillis(),
                    color = color,
                    isPinned = isPinned,
                    tags = tags
                )
            )
        }
    }

    fun togglePin(note: NoteEntity) {
        viewModelScope.launch {
            repository.insertNote(note.copy(isPinned = !note.isPinned))
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    fun searchNotes(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        viewModelScope.launch {
            _searchResults.value = repository.searchNotes(query)
        }
    }
}
