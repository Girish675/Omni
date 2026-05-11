package com.girish.premiumapp.`data`.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performBlocking
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.girish.premiumapp.domain.model.EventEntity
import com.girish.premiumapp.domain.model.RecurrenceRule
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class EventDao_Impl(
  __db: RoomDatabase,
) : EventDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfEventEntity: EntityInsertAdapter<EventEntity>

  private val __updateAdapterOfEventEntity: EntityDeleteOrUpdateAdapter<EventEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfEventEntity = object : EntityInsertAdapter<EventEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `events` (`id`,`title`,`description`,`startTime`,`endTime`,`isAllDay`,`recurrenceRule`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: EventEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        statement.bindLong(4, entity.startTime)
        statement.bindLong(5, entity.endTime)
        val _tmp: Int = if (entity.isAllDay) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindText(7, __RecurrenceRule_enumToString(entity.recurrenceRule))
      }
    }
    this.__updateAdapterOfEventEntity = object : EntityDeleteOrUpdateAdapter<EventEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `events` SET `id` = ?,`title` = ?,`description` = ?,`startTime` = ?,`endTime` = ?,`isAllDay` = ?,`recurrenceRule` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: EventEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        statement.bindLong(4, entity.startTime)
        statement.bindLong(5, entity.endTime)
        val _tmp: Int = if (entity.isAllDay) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindText(7, __RecurrenceRule_enumToString(entity.recurrenceRule))
        statement.bindLong(8, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertEvent(event: EventEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfEventEntity.insert(_connection, event)
  }

  public override suspend fun updateEvent(event: EventEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfEventEntity.handle(_connection, event)
  }

  public override fun getAllEvents(): Flow<List<EventEntity>> {
    val _sql: String = "SELECT * FROM events ORDER BY startTime ASC"
    return createFlow(__db, false, arrayOf("events")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfIsAllDay: Int = getColumnIndexOrThrow(_stmt, "isAllDay")
        val _columnIndexOfRecurrenceRule: Int = getColumnIndexOrThrow(_stmt, "recurrenceRule")
        val _result: MutableList<EventEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: EventEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpIsAllDay: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAllDay).toInt()
          _tmpIsAllDay = _tmp != 0
          val _tmpRecurrenceRule: RecurrenceRule
          _tmpRecurrenceRule =
              __RecurrenceRule_stringToEnum(_stmt.getText(_columnIndexOfRecurrenceRule))
          _item =
              EventEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpIsAllDay,_tmpRecurrenceRule)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchEvents(query: String): List<EventEntity> {
    val _sql: String =
        "SELECT * FROM events WHERE title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfIsAllDay: Int = getColumnIndexOrThrow(_stmt, "isAllDay")
        val _columnIndexOfRecurrenceRule: Int = getColumnIndexOrThrow(_stmt, "recurrenceRule")
        val _result: MutableList<EventEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: EventEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpIsAllDay: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAllDay).toInt()
          _tmpIsAllDay = _tmp != 0
          val _tmpRecurrenceRule: RecurrenceRule
          _tmpRecurrenceRule =
              __RecurrenceRule_stringToEnum(_stmt.getText(_columnIndexOfRecurrenceRule))
          _item =
              EventEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpIsAllDay,_tmpRecurrenceRule)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllEventsList(): List<EventEntity> {
    val _sql: String = "SELECT * FROM events ORDER BY startTime ASC"
    return performBlocking(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfIsAllDay: Int = getColumnIndexOrThrow(_stmt, "isAllDay")
        val _columnIndexOfRecurrenceRule: Int = getColumnIndexOrThrow(_stmt, "recurrenceRule")
        val _result: MutableList<EventEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: EventEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpStartTime: Long
          _tmpStartTime = _stmt.getLong(_columnIndexOfStartTime)
          val _tmpEndTime: Long
          _tmpEndTime = _stmt.getLong(_columnIndexOfEndTime)
          val _tmpIsAllDay: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAllDay).toInt()
          _tmpIsAllDay = _tmp != 0
          val _tmpRecurrenceRule: RecurrenceRule
          _tmpRecurrenceRule =
              __RecurrenceRule_stringToEnum(_stmt.getText(_columnIndexOfRecurrenceRule))
          _item =
              EventEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpIsAllDay,_tmpRecurrenceRule)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTodayEventCount(startOfDay: Long, endOfDay: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM events WHERE startTime >= ? AND startTime < ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, startOfDay)
        _argIndex = 2
        _stmt.bindLong(_argIndex, endOfDay)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteEvent(eventId: Int) {
    val _sql: String = "DELETE FROM events WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, eventId.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  private fun __RecurrenceRule_enumToString(_value: RecurrenceRule): String = when (_value) {
    RecurrenceRule.NONE -> "NONE"
    RecurrenceRule.DAILY -> "DAILY"
    RecurrenceRule.WEEKLY -> "WEEKLY"
    RecurrenceRule.MONTHLY -> "MONTHLY"
    RecurrenceRule.YEARLY -> "YEARLY"
  }

  private fun __RecurrenceRule_stringToEnum(_value: String): RecurrenceRule = when (_value) {
    "NONE" -> RecurrenceRule.NONE
    "DAILY" -> RecurrenceRule.DAILY
    "WEEKLY" -> RecurrenceRule.WEEKLY
    "MONTHLY" -> RecurrenceRule.MONTHLY
    "YEARLY" -> RecurrenceRule.YEARLY
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
