package com.girish.premiumapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girish.premiumapp.domain.model.EventEntity
import com.girish.premiumapp.domain.model.ExpenseEntity
import com.girish.premiumapp.domain.model.NoteEntity
import com.girish.premiumapp.domain.model.TaskEntity
import com.girish.premiumapp.domain.repository.EventRepository
import com.girish.premiumapp.domain.repository.ExpenseRepository
import com.girish.premiumapp.domain.repository.NoteRepository
import com.girish.premiumapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchResults(
    val tasks: List<TaskEntity> = emptyList(),
    val notes: List<NoteEntity> = emptyList(),
    val expenses: List<ExpenseEntity> = emptyList(),
    val events: List<EventEntity> = emptyList(),
    val isSearching: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val noteRepository: NoteRepository,
    private val expenseRepository: ExpenseRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _results = MutableStateFlow(SearchResults())
    val results: StateFlow<SearchResults> = _results.asStateFlow()

    fun search(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _results.value = SearchResults()
            return
        }
        _results.value = _results.value.copy(isSearching = true)
        viewModelScope.launch {
            val tasks = taskRepository.searchTasks(query)
            val notes = noteRepository.searchNotes(query)
            val expenses = expenseRepository.searchExpenses(query)
            val events = eventRepository.searchEvents(query)
            _results.value = SearchResults(
                tasks = tasks,
                notes = notes,
                expenses = expenses,
                events = events,
                isSearching = false
            )
        }
    }
}
