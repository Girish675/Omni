package com.girish.premiumapp.presentation.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.girish.premiumapp.domain.repository.EventRepository
import com.girish.premiumapp.domain.repository.ExpenseRepository
import com.girish.premiumapp.domain.repository.NoteRepository
import com.girish.premiumapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class DashboardStats(
    val pendingTasks: Int = 0,
    val totalTasks: Int = 0,
    val noteCount: Int = 0,
    val todayEvents: Int = 0,
    val monthlySpent: Double = 0.0,
    val userName: String = ""
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val noteRepository: NoteRepository,
    private val eventRepository: EventRepository,
    private val expenseRepository: ExpenseRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _stats = MutableStateFlow(DashboardStats())
    val stats: StateFlow<DashboardStats> = _stats.asStateFlow()

    init {
        loadStats()
    }

    fun loadStats() {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val userName = context.getSharedPreferences("account_prefs", Context.MODE_PRIVATE)
                .getString("display_name", "") ?: ""

            val pendingTasks = taskRepository.getPendingTaskCount()
            val totalTasks = taskRepository.getTotalTaskCount()
            val noteCount = noteRepository.getNoteCount()

            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            val startOfDay = cal.timeInMillis
            cal.add(Calendar.DAY_OF_YEAR, 1)
            val endOfDay = cal.timeInMillis

            val todayEvents = eventRepository.getTodayEventCount(startOfDay, endOfDay)

            // Get monthly total
            val now = Calendar.getInstance()
            now.set(Calendar.DAY_OF_MONTH, 1)
            now.set(Calendar.HOUR_OF_DAY, 0)
            now.set(Calendar.MINUTE, 0)
            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)
            val startOfMonth = now.timeInMillis
            now.add(Calendar.MONTH, 1)
            val endOfMonth = now.timeInMillis

            val monthlySpent = expenseRepository.getMonthlyTotal(startOfMonth, endOfMonth) ?: 0.0

            _stats.value = DashboardStats(
                pendingTasks = pendingTasks,
                totalTasks = totalTasks,
                noteCount = noteCount,
                todayEvents = todayEvents,
                monthlySpent = monthlySpent,
                userName = userName
            )
        }
    }
}
