package com.example.notesappmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.notesappmvvm.dao.NotesDao
import com.example.notesappmvvm.database.NotesDatabase
import com.example.notesappmvvm.repository.NotesRepository
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): NotesDatabase {
        val roomDatabaseInstance =
            Room.databaseBuilder(context, NotesDatabase::class.java, "Notes")
                .allowMainThreadQueries().build()
        return roomDatabaseInstance
    }

    @Singleton
    @Provides
    fun provideDao(database: NotesDatabase): NotesDao {
        return database.myNotesDao()
    }

    @Singleton
    @Provides
    fun provideRepo(dao: NotesDao): NotesRepository {
        return NotesRepository(dao)
    }
}