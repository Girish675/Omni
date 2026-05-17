package com.girish.premiumapp.domain.repository

import com.girish.premiumapp.data.local.CategorySpending
import com.girish.premiumapp.data.local.DailySpending
import com.girish.premiumapp.domain.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    suspend fun insertExpense(expense: ExpenseEntity)
    suspend fun updateExpense(expense: ExpenseEntity)
    suspend fun deleteExpense(expenseId: Int)
    suspend fun getExpenseById(expenseId: Int): ExpenseEntity?
    suspend fun searchExpenses(query: String): List<ExpenseEntity>
    suspend fun getMonthlyTotal(startOfMonth: Long, endOfMonth: Long): Double?
    suspend fun getTotalSpent(): Double?
    suspend fun getSpendingByCategory(): List<CategorySpending>
    suspend fun getDailySpending(startDate: Long): List<DailySpending>
}
