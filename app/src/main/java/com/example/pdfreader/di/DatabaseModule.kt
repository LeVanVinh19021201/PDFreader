package com.example.pdfreader.di

import android.content.Context
import androidx.room.Room
import com.example.pdfreader.database.AppDao
import com.example.pdfreader.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "appdb").build()
    }

    @Provides
    fun provideDao(database: AppDatabase): AppDao {
        return database.getDao()
    }
}