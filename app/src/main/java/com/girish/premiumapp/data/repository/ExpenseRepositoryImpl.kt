package com.girish.premiumapp.data.repository

import com.girish.premiumapp.data.local.CategorySpending
import com.girish.premiumapp.data.local.DailySpending
import com.girish.premiumapp.data.local.ExpenseDao
import com.girish.premiumapp.domain.model.ExpenseEntity
import com.girish.premiumapp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val dao: ExpenseDao
) : ExpenseRepository {
    override fun getAllExpenses(): Flow<List<ExpenseEntity>> = dao.getAllExpenses()
    override suspend fun insertExpense(expense: ExpenseEntity) = dao.insertExpense(expense)
    override suspend fun updateExpense(expense: ExpenseEntity) = dao.updateExpense(expense)
    override suspend fun deleteExpense(expenseId: Int) = dao.deleteExpense(expenseId)
    override suspend fun getExpenseById(expenseId: Int): ExpenseEntity? = dao.getExpenseById(expenseId)
    override suspend fun searchExpenses(query: String): List<ExpenseEntity> = dao.searchExpenses(query)
    override suspend fun getMonthlyTotal(startOfMonth: Long, endOfMonth: Long): Double? = dao.getMonthlyTotal(startOfMonth, endOfMonth)
    override suspend fun getTotalSpent(): Double? = dao.getTotalSpent()
    override suspend fun getSpendingByCategory(): List<CategorySpending> = dao.getSpendingByCategory()
    override suspend fun getDailySpending(startDate: Long): List<DailySpending> = dao.getDailySpending(startDate)
}
