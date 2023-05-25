package com.example.pdfreader.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =[DataFile::class] , version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    abstract fun getDao() : AppDao
}