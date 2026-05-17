package com.girish.premiumapp.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.girish.premiumapp.data.local.CategorySpending
import com.girish.premiumapp.domain.model.ExpenseEntity
import com.girish.premiumapp.domain.model.PaymentMethod
import com.girish.premiumapp.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class AnalyticsData(
    val categorySpending: List<CategorySpending> = emptyList(),
    val monthlyTotal: Double = 0.0,
    val totalSpent: Double = 0.0
)

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses.asStateFlow()

    private val _analytics = MutableStateFlow(AnalyticsData())
    val analytics: StateFlow<AnalyticsData> = _analytics.asStateFlow()

    init {
        getExpenses()
        loadAnalytics()
    }

    private fun getExpenses() {
        repository.getAllExpenses().onEach { list ->
            _expenses.value = list
        }.launchIn(viewModelScope)
    }

    fun loadAnalytics() {
        viewModelScope.launch {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_MONTH, 1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            val startOfMonth = cal.timeInMillis
            cal.add(Calendar.MONTH, 1)
            val endOfMonth = cal.timeInMillis

            val monthlyTotal = repository.getMonthlyTotal(startOfMonth, endOfMonth) ?: 0.0
            val totalSpent = repository.getTotalSpent() ?: 0.0
            val categorySpending = repository.getSpendingByCategory()

            _analytics.value = AnalyticsData(
                categorySpending = categorySpending,
                monthlyTotal = monthlyTotal,
                totalSpent = totalSpent
            )
        }
    }

    fun addExpense(amount: Double, category: String, note: String, paymentMethod: PaymentMethod, imageUri: String? = null) {
        viewModelScope.launch {
            repository.insertExpense(
                ExpenseEntity(
                    amount = amount,
                    category = category,
                    note = note,
                    paymentMethod = paymentMethod,
                    imageUri = imageUri
                )
            )
            loadAnalytics()
        }
    }

    fun updateExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.updateExpense(expense)
            loadAnalytics()
        }
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch {
            repository.deleteExpense(expenseId)
            loadAnalytics()
        }
    }
}
