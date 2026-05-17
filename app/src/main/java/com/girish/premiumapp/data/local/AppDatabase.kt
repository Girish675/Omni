package com.girish.premiumapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.girish.premiumapp.domain.model.EventEntity
import com.girish.premiumapp.domain.model.ExpenseEntity
import com.girish.premiumapp.domain.model.NoteEntity
import com.girish.premiumapp.domain.model.TaskEntity

@Database(
    entities = [TaskEntity::class, ExpenseEntity::class, NoteEntity::class, EventEntity::class],
    version = 8,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val expenseDao: ExpenseDao
    abstract val noteDao: NoteDao
    abstract val eventDao: EventDao
    
    companion object {
        const val DATABASE_NAME = "premium_app_db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `expenses` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`amount` REAL NOT NULL, " +
                    "`category` TEXT NOT NULL, " +
                    "`note` TEXT NOT NULL, " +
                    "`paymentMethod` TEXT NOT NULL, " +
                    "`date` INTEGER NOT NULL)"
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `notes` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`content` TEXT NOT NULL, " +
                    "`lastModified` INTEGER NOT NULL)"
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `events` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`description` TEXT NOT NULL, " +
                    "`startTime` INTEGER NOT NULL, " +
                    "`endTime` INTEGER NOT NULL, " +
                    "`isAllDay` INTEGER NOT NULL)"
                )
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE expenses ADD COLUMN imageUri TEXT DEFAULT NULL")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Tasks: add priority and dueDate
                db.execSQL("ALTER TABLE tasks ADD COLUMN priority TEXT NOT NULL DEFAULT 'MEDIUM'")
                db.execSQL("ALTER TABLE tasks ADD COLUMN dueDate INTEGER DEFAULT NULL")
                // Notes: add color, isPinned, tags
                db.execSQL("ALTER TABLE notes ADD COLUMN color INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE notes ADD COLUMN isPinned INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE notes ADD COLUMN tags TEXT NOT NULL DEFAULT ''")
                // Events: add recurrenceRule
                db.execSQL("ALTER TABLE events ADD COLUMN recurrenceRule TEXT NOT NULL DEFAULT 'NONE'")
            }
        }

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasks ADD COLUMN subtasks TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE tasks ADD COLUMN tags TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE tasks ADD COLUMN estimatedTimeMinutes INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE tasks ADD COLUMN isHabit INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE tasks ADD COLUMN habitStreak INTEGER NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasks ADD COLUMN recurrenceRule TEXT NOT NULL DEFAULT 'NONE'")
                db.execSQL("ALTER TABLE tasks ADD COLUMN reminderTime INTEGER DEFAULT NULL")
            }
        }
    }
}
