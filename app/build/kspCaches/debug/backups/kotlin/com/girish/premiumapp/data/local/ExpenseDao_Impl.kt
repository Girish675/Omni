package com.girish.premiumapp.`data`.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performBlocking
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.girish.premiumapp.domain.model.ExpenseEntity
import com.girish.premiumapp.domain.model.PaymentMethod
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.IllegalArgumentException
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ExpenseDao_Impl(
  __db: RoomDatabase,
) : ExpenseDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfExpenseEntity: EntityInsertAdapter<ExpenseEntity>

  private val __updateAdapterOfExpenseEntity: EntityDeleteOrUpdateAdapter<ExpenseEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfExpenseEntity = object : EntityInsertAdapter<ExpenseEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `expenses` (`id`,`amount`,`category`,`note`,`paymentMethod`,`date`,`imageUri`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ExpenseEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindDouble(2, entity.amount)
        statement.bindText(3, entity.category)
        statement.bindText(4, entity.note)
        statement.bindText(5, __PaymentMethod_enumToString(entity.paymentMethod))
        statement.bindLong(6, entity.date)
        val _tmpImageUri: String? = entity.imageUri
        if (_tmpImageUri == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpImageUri)
        }
      }
    }
    this.__updateAdapterOfExpenseEntity = object : EntityDeleteOrUpdateAdapter<ExpenseEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `expenses` SET `id` = ?,`amount` = ?,`category` = ?,`note` = ?,`paymentMethod` = ?,`date` = ?,`imageUri` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ExpenseEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindDouble(2, entity.amount)
        statement.bindText(3, entity.category)
        statement.bindText(4, entity.note)
        statement.bindText(5, __PaymentMethod_enumToString(entity.paymentMethod))
        statement.bindLong(6, entity.date)
        val _tmpImageUri: String? = entity.imageUri
        if (_tmpImageUri == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpImageUri)
        }
        statement.bindLong(8, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertExpense(expense: ExpenseEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfExpenseEntity.insert(_connection, expense)
  }

  public override suspend fun updateExpense(expense: ExpenseEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfExpenseEntity.handle(_connection, expense)
  }

  public override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
    val _sql: String = "SELECT * FROM expenses ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("expenses")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfImageUri: Int = getColumnIndexOrThrow(_stmt, "imageUri")
        val _result: MutableList<ExpenseEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExpenseEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpAmount: Double
          _tmpAmount = _stmt.getDouble(_columnIndexOfAmount)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpNote: String
          _tmpNote = _stmt.getText(_columnIndexOfNote)
          val _tmpPaymentMethod: PaymentMethod
          _tmpPaymentMethod =
              __PaymentMethod_stringToEnum(_stmt.getText(_columnIndexOfPaymentMethod))
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpImageUri: String?
          if (_stmt.isNull(_columnIndexOfImageUri)) {
            _tmpImageUri = null
          } else {
            _tmpImageUri = _stmt.getText(_columnIndexOfImageUri)
          }
          _item =
              ExpenseEntity(_tmpId,_tmpAmount,_tmpCategory,_tmpNote,_tmpPaymentMethod,_tmpDate,_tmpImageUri)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getExpenseById(expenseId: Int): ExpenseEntity? {
    val _sql: String = "SELECT * FROM expenses WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, expenseId.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfImageUri: Int = getColumnIndexOrThrow(_stmt, "imageUri")
        val _result: ExpenseEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpAmount: Double
          _tmpAmount = _stmt.getDouble(_columnIndexOfAmount)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpNote: String
          _tmpNote = _stmt.getText(_columnIndexOfNote)
          val _tmpPaymentMethod: PaymentMethod
          _tmpPaymentMethod =
              __PaymentMethod_stringToEnum(_stmt.getText(_columnIndexOfPaymentMethod))
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpImageUri: String?
          if (_stmt.isNull(_columnIndexOfImageUri)) {
            _tmpImageUri = null
          } else {
            _tmpImageUri = _stmt.getText(_columnIndexOfImageUri)
          }
          _result =
              ExpenseEntity(_tmpId,_tmpAmount,_tmpCategory,_tmpNote,_tmpPaymentMethod,_tmpDate,_tmpImageUri)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchExpenses(query: String): List<ExpenseEntity> {
    val _sql: String =
        "SELECT * FROM expenses WHERE category LIKE '%' || ? || '%' OR note LIKE '%' || ? || '%'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfImageUri: Int = getColumnIndexOrThrow(_stmt, "imageUri")
        val _result: MutableList<ExpenseEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExpenseEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpAmount: Double
          _tmpAmount = _stmt.getDouble(_columnIndexOfAmount)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpNote: String
          _tmpNote = _stmt.getText(_columnIndexOfNote)
          val _tmpPaymentMethod: PaymentMethod
          _tmpPaymentMethod =
              __PaymentMethod_stringToEnum(_stmt.getText(_columnIndexOfPaymentMethod))
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpImageUri: String?
          if (_stmt.isNull(_columnIndexOfImageUri)) {
            _tmpImageUri = null
          } else {
            _tmpImageUri = _stmt.getText(_columnIndexOfImageUri)
          }
          _item =
              ExpenseEntity(_tmpId,_tmpAmount,_tmpCategory,_tmpNote,_tmpPaymentMethod,_tmpDate,_tmpImageUri)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllExpensesList(): List<ExpenseEntity> {
    val _sql: String = "SELECT * FROM expenses ORDER BY date DESC"
    return performBlocking(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAmount: Int = getColumnIndexOrThrow(_stmt, "amount")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfNote: Int = getColumnIndexOrThrow(_stmt, "note")
        val _columnIndexOfPaymentMethod: Int = getColumnIndexOrThrow(_stmt, "paymentMethod")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfImageUri: Int = getColumnIndexOrThrow(_stmt, "imageUri")
        val _result: MutableList<ExpenseEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExpenseEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpAmount: Double
          _tmpAmount = _stmt.getDouble(_columnIndexOfAmount)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpNote: String
          _tmpNote = _stmt.getText(_columnIndexOfNote)
          val _tmpPaymentMethod: PaymentMethod
          _tmpPaymentMethod =
              __PaymentMethod_stringToEnum(_stmt.getText(_columnIndexOfPaymentMethod))
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpImageUri: String?
          if (_stmt.isNull(_columnIndexOfImageUri)) {
            _tmpImageUri = null
          } else {
            _tmpImageUri = _stmt.getText(_columnIndexOfImageUri)
          }
          _item =
              ExpenseEntity(_tmpId,_tmpAmount,_tmpCategory,_tmpNote,_tmpPaymentMethod,_tmpDate,_tmpImageUri)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getMonthlyTotal(startOfMonth: Long, endOfMonth: Long): Double? {
    val _sql: String = "SELECT SUM(amount) FROM expenses WHERE date >= ? AND date < ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, startOfMonth)
        _argIndex = 2
        _stmt.bindLong(_argIndex, endOfMonth)
        val _result: Double?
        if (_stmt.step()) {
          val _tmp: Double?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getDouble(0)
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalSpent(): Double? {
    val _sql: String = "SELECT SUM(amount) FROM expenses"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Double?
        if (_stmt.step()) {
          val _tmp: Double?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getDouble(0)
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSpendingByCategory(): List<CategorySpending> {
    val _sql: String =
        "SELECT category, SUM(amount) as total FROM expenses GROUP BY category ORDER BY total DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfCategory: Int = 0
        val _columnIndexOfTotal: Int = 1
        val _result: MutableList<CategorySpending> = mutableListOf()
        while (_stmt.step()) {
          val _item: CategorySpending
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpTotal: Double
          _tmpTotal = _stmt.getDouble(_columnIndexOfTotal)
          _item = CategorySpending(_tmpCategory,_tmpTotal)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDailySpending(startDate: Long): List<DailySpending> {
    val _sql: String =
        "SELECT date, SUM(amount) as total FROM expenses WHERE date >= ? GROUP BY strftime('%Y-%m-%d', date/1000, 'unixepoch') ORDER BY date ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, startDate)
        val _columnIndexOfDate: Int = 0
        val _columnIndexOfTotal: Int = 1
        val _result: MutableList<DailySpending> = mutableListOf()
        while (_stmt.step()) {
          val _item: DailySpending
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpTotal: Double
          _tmpTotal = _stmt.getDouble(_columnIndexOfTotal)
          _item = DailySpending(_tmpDate,_tmpTotal)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteExpense(expenseId: Int) {
    val _sql: String = "DELETE FROM expenses WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, expenseId.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  private fun __PaymentMethod_enumToString(_value: PaymentMethod): String = when (_value) {
    PaymentMethod.CREDIT_CARD -> "CREDIT_CARD"
    PaymentMethod.DEBIT_CARD -> "DEBIT_CARD"
    PaymentMethod.BANK_TRANSFER -> "BANK_TRANSFER"
    PaymentMethod.CASH -> "CASH"
    PaymentMethod.UPI -> "UPI"
  }

  private fun __PaymentMethod_stringToEnum(_value: String): PaymentMethod = when (_value) {
    "CREDIT_CARD" -> PaymentMethod.CREDIT_CARD
    "DEBIT_CARD" -> PaymentMethod.DEBIT_CARD
    "BANK_TRANSFER" -> PaymentMethod.BANK_TRANSFER
    "CASH" -> PaymentMethod.CASH
    "UPI" -> PaymentMethod.UPI
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
