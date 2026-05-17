package com.girish.premiumapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.girish.premiumapp.domain.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpense(expenseId: Int)

    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: Int): ExpenseEntity?

    @Query("SELECT * FROM expenses WHERE category LIKE '%' || :query || '%' OR note LIKE '%' || :query || '%'")
    suspend fun searchExpenses(query: String): List<ExpenseEntity>

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpensesList(): List<ExpenseEntity>

    @Query("SELECT SUM(amount) FROM expenses WHERE date >= :startOfMonth AND date < :endOfMonth")
    suspend fun getMonthlyTotal(startOfMonth: Long, endOfMonth: Long): Double?

    @Query("SELECT SUM(amount) FROM expenses")
    suspend fun getTotalSpent(): Double?

    @Query("SELECT category, SUM(amount) as total FROM expenses GROUP BY category ORDER BY total DESC")
    suspend fun getSpendingByCategory(): List<CategorySpending>

    @Query("SELECT date, SUM(amount) as total FROM expenses WHERE date >= :startDate GROUP BY strftime('%Y-%m-%d', date/1000, 'unixepoch') ORDER BY date ASC")
    suspend fun getDailySpending(startDate: Long): List<DailySpending>
}

data class CategorySpending(
    val category: String,
    val total: Double
)

data class DailySpending(
    val date: Long,
    val total: Double
)
