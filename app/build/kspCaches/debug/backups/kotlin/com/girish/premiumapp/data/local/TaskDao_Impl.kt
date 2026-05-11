package com.girish.premiumapp.`data`.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performBlocking
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.girish.premiumapp.domain.model.TaskEntity
import com.girish.premiumapp.domain.model.TaskPriority
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
public class TaskDao_Impl(
  __db: RoomDatabase,
) : TaskDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTaskEntity: EntityInsertAdapter<TaskEntity>

  private val __updateAdapterOfTaskEntity: EntityDeleteOrUpdateAdapter<TaskEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTaskEntity = object : EntityInsertAdapter<TaskEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `tasks` (`id`,`title`,`description`,`isCompleted`,`createdAt`,`priority`,`dueDate`,`subtasks`,`tags`,`estimatedTimeMinutes`,`isHabit`,`habitStreak`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TaskEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        val _tmp: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.createdAt)
        statement.bindText(6, __TaskPriority_enumToString(entity.priority))
        val _tmpDueDate: Long? = entity.dueDate
        if (_tmpDueDate == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpDueDate)
        }
        statement.bindText(8, entity.subtasks)
        statement.bindText(9, entity.tags)
        statement.bindLong(10, entity.estimatedTimeMinutes.toLong())
        val _tmp_1: Int = if (entity.isHabit) 1 else 0
        statement.bindLong(11, _tmp_1.toLong())
        statement.bindLong(12, entity.habitStreak.toLong())
      }
    }
    this.__updateAdapterOfTaskEntity = object : EntityDeleteOrUpdateAdapter<TaskEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`description` = ?,`isCompleted` = ?,`createdAt` = ?,`priority` = ?,`dueDate` = ?,`subtasks` = ?,`tags` = ?,`estimatedTimeMinutes` = ?,`isHabit` = ?,`habitStreak` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TaskEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        val _tmp: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.createdAt)
        statement.bindText(6, __TaskPriority_enumToString(entity.priority))
        val _tmpDueDate: Long? = entity.dueDate
        if (_tmpDueDate == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpDueDate)
        }
        statement.bindText(8, entity.subtasks)
        statement.bindText(9, entity.tags)
        statement.bindLong(10, entity.estimatedTimeMinutes.toLong())
        val _tmp_1: Int = if (entity.isHabit) 1 else 0
        statement.bindLong(11, _tmp_1.toLong())
        statement.bindLong(12, entity.habitStreak.toLong())
        statement.bindLong(13, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertTask(task: TaskEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfTaskEntity.insert(_connection, task)
  }

  public override suspend fun updateTask(task: TaskEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfTaskEntity.handle(_connection, task)
  }

  public override fun getAllTasks(): Flow<List<TaskEntity>> {
    val _sql: String = "SELECT * FROM tasks ORDER BY isCompleted ASC, createdAt DESC"
    return createFlow(__db, false, arrayOf("tasks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfDueDate: Int = getColumnIndexOrThrow(_stmt, "dueDate")
        val _columnIndexOfSubtasks: Int = getColumnIndexOrThrow(_stmt, "subtasks")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfEstimatedTimeMinutes: Int = getColumnIndexOrThrow(_stmt,
            "estimatedTimeMinutes")
        val _columnIndexOfIsHabit: Int = getColumnIndexOrThrow(_stmt, "isHabit")
        val _columnIndexOfHabitStreak: Int = getColumnIndexOrThrow(_stmt, "habitStreak")
        val _result: MutableList<TaskEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TaskEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpIsCompleted: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpPriority: TaskPriority
          _tmpPriority = __TaskPriority_stringToEnum(_stmt.getText(_columnIndexOfPriority))
          val _tmpDueDate: Long?
          if (_stmt.isNull(_columnIndexOfDueDate)) {
            _tmpDueDate = null
          } else {
            _tmpDueDate = _stmt.getLong(_columnIndexOfDueDate)
          }
          val _tmpSubtasks: String
          _tmpSubtasks = _stmt.getText(_columnIndexOfSubtasks)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpEstimatedTimeMinutes: Int
          _tmpEstimatedTimeMinutes = _stmt.getLong(_columnIndexOfEstimatedTimeMinutes).toInt()
          val _tmpIsHabit: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsHabit).toInt()
          _tmpIsHabit = _tmp_1 != 0
          val _tmpHabitStreak: Int
          _tmpHabitStreak = _stmt.getLong(_columnIndexOfHabitStreak).toInt()
          _item =
              TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpCreatedAt,_tmpPriority,_tmpDueDate,_tmpSubtasks,_tmpTags,_tmpEstimatedTimeMinutes,_tmpIsHabit,_tmpHabitStreak)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchTasks(query: String): List<TaskEntity> {
    val _sql: String =
        "SELECT * FROM tasks WHERE title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'"
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
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfDueDate: Int = getColumnIndexOrThrow(_stmt, "dueDate")
        val _columnIndexOfSubtasks: Int = getColumnIndexOrThrow(_stmt, "subtasks")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfEstimatedTimeMinutes: Int = getColumnIndexOrThrow(_stmt,
            "estimatedTimeMinutes")
        val _columnIndexOfIsHabit: Int = getColumnIndexOrThrow(_stmt, "isHabit")
        val _columnIndexOfHabitStreak: Int = getColumnIndexOrThrow(_stmt, "habitStreak")
        val _result: MutableList<TaskEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TaskEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpIsCompleted: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpPriority: TaskPriority
          _tmpPriority = __TaskPriority_stringToEnum(_stmt.getText(_columnIndexOfPriority))
          val _tmpDueDate: Long?
          if (_stmt.isNull(_columnIndexOfDueDate)) {
            _tmpDueDate = null
          } else {
            _tmpDueDate = _stmt.getLong(_columnIndexOfDueDate)
          }
          val _tmpSubtasks: String
          _tmpSubtasks = _stmt.getText(_columnIndexOfSubtasks)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpEstimatedTimeMinutes: Int
          _tmpEstimatedTimeMinutes = _stmt.getLong(_columnIndexOfEstimatedTimeMinutes).toInt()
          val _tmpIsHabit: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsHabit).toInt()
          _tmpIsHabit = _tmp_1 != 0
          val _tmpHabitStreak: Int
          _tmpHabitStreak = _stmt.getLong(_columnIndexOfHabitStreak).toInt()
          _item =
              TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpCreatedAt,_tmpPriority,_tmpDueDate,_tmpSubtasks,_tmpTags,_tmpEstimatedTimeMinutes,_tmpIsHabit,_tmpHabitStreak)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllTasksList(): List<TaskEntity> {
    val _sql: String = "SELECT * FROM tasks ORDER BY createdAt DESC"
    return performBlocking(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfDueDate: Int = getColumnIndexOrThrow(_stmt, "dueDate")
        val _columnIndexOfSubtasks: Int = getColumnIndexOrThrow(_stmt, "subtasks")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfEstimatedTimeMinutes: Int = getColumnIndexOrThrow(_stmt,
            "estimatedTimeMinutes")
        val _columnIndexOfIsHabit: Int = getColumnIndexOrThrow(_stmt, "isHabit")
        val _columnIndexOfHabitStreak: Int = getColumnIndexOrThrow(_stmt, "habitStreak")
        val _result: MutableList<TaskEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TaskEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpIsCompleted: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpPriority: TaskPriority
          _tmpPriority = __TaskPriority_stringToEnum(_stmt.getText(_columnIndexOfPriority))
          val _tmpDueDate: Long?
          if (_stmt.isNull(_columnIndexOfDueDate)) {
            _tmpDueDate = null
          } else {
            _tmpDueDate = _stmt.getLong(_columnIndexOfDueDate)
          }
          val _tmpSubtasks: String
          _tmpSubtasks = _stmt.getText(_columnIndexOfSubtasks)
          val _tmpTags: String
          _tmpTags = _stmt.getText(_columnIndexOfTags)
          val _tmpEstimatedTimeMinutes: Int
          _tmpEstimatedTimeMinutes = _stmt.getLong(_columnIndexOfEstimatedTimeMinutes).toInt()
          val _tmpIsHabit: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsHabit).toInt()
          _tmpIsHabit = _tmp_1 != 0
          val _tmpHabitStreak: Int
          _tmpHabitStreak = _stmt.getLong(_columnIndexOfHabitStreak).toInt()
          _item =
              TaskEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpIsCompleted,_tmpCreatedAt,_tmpPriority,_tmpDueDate,_tmpSubtasks,_tmpTags,_tmpEstimatedTimeMinutes,_tmpIsHabit,_tmpHabitStreak)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPendingTaskCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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

  public override suspend fun getTotalTaskCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM tasks"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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

  public override suspend fun deleteTask(taskId: Int) {
    val _sql: String = "DELETE FROM tasks WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, taskId.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
    val _sql: String = "UPDATE tasks SET isCompleted = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isCompleted) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, taskId.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  private fun __TaskPriority_enumToString(_value: TaskPriority): String = when (_value) {
    TaskPriority.LOW -> "LOW"
    TaskPriority.MEDIUM -> "MEDIUM"
    TaskPriority.HIGH -> "HIGH"
    TaskPriority.URGENT -> "URGENT"
  }

  private fun __TaskPriority_stringToEnum(_value: String): TaskPriority = when (_value) {
    "LOW" -> TaskPriority.LOW
    "MEDIUM" -> TaskPriority.MEDIUM
    "HIGH" -> TaskPriority.HIGH
    "URGENT" -> TaskPriority.URGENT
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
