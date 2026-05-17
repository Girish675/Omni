package com.girish.premiumapp.di

import android.app.Application
import androidx.room.Room
import com.girish.premiumapp.data.local.AppDatabase
import com.girish.premiumapp.data.local.EventDao
import com.girish.premiumapp.data.local.ExpenseDao
import com.girish.premiumapp.data.local.NoteDao
import com.girish.premiumapp.data.local.TaskDao
import com.girish.premiumapp.data.repository.EventRepositoryImpl
import com.girish.premiumapp.data.repository.ExpenseRepositoryImpl
import com.girish.premiumapp.data.repository.NoteRepositoryImpl
import com.girish.premiumapp.data.repository.TaskRepositoryImpl
import com.girish.premiumapp.domain.repository.EventRepository
import com.girish.premiumapp.domain.repository.ExpenseRepository
import com.girish.premiumapp.domain.repository.NoteRepository
import com.girish.premiumapp.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
        .addMigrations(
            AppDatabase.MIGRATION_1_2,
            AppDatabase.MIGRATION_2_3,
            AppDatabase.MIGRATION_3_4,
            AppDatabase.MIGRATION_4_5,
            AppDatabase.MIGRATION_5_6,
            AppDatabase.MIGRATION_6_7,
            AppDatabase.MIGRATION_7_8
        )
        .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase): TaskDao = db.taskDao

    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository = TaskRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideExpenseDao(db: AppDatabase): ExpenseDao = db.expenseDao

    @Provides
    @Singleton
    fun provideExpenseRepository(dao: ExpenseDao): ExpenseRepository = ExpenseRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideNoteDao(db: AppDatabase): NoteDao = db.noteDao

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDao): NoteRepository = NoteRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideEventDao(db: AppDatabase): EventDao = db.eventDao

    @Provides
    @Singleton
    fun provideEventRepository(dao: EventDao): EventRepository = EventRepositoryImpl(dao)
}
