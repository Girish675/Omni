package com.girish.premiumapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, CASH, UPI
}

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val category: String,
    val note: String,
    val paymentMethod: PaymentMethod,
    val date: Long = System.currentTimeMillis(),
    val imageUri: String? = null
)
