package com.girish.premiumapp.presentation.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girish.premiumapp.domain.model.EventEntity
import com.girish.premiumapp.domain.model.RecurrenceRule
import com.girish.premiumapp.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<EventEntity>>(emptyList())
    val events: StateFlow<List<EventEntity>> = _events.asStateFlow()

    init {
        getEvents()
    }

    private fun getEvents() {
        repository.getAllEvents().onEach { list ->
            _events.value = list
        }.launchIn(viewModelScope)
    }

    fun addEvent(title: String, description: String, startTime: Long, endTime: Long, isAllDay: Boolean, recurrenceRule: RecurrenceRule = RecurrenceRule.NONE) {
        viewModelScope.launch {
            repository.insertEvent(
                EventEntity(
                    title = title,
                    description = description,
                    startTime = startTime,
                    endTime = endTime,
                    isAllDay = isAllDay,
                    recurrenceRule = recurrenceRule
                )
            )
        }
    }

    fun updateEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.updateEvent(event)
        }
    }

    fun deleteEvent(eventId: Int) {
        viewModelScope.launch {
            repository.deleteEvent(eventId)
        }
    }
}
