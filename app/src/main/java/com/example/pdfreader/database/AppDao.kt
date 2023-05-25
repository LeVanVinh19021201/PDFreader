package com.example.pdfreader.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Query("SELECT * FROM file_pdf_table ORDER BY id ASC")
    fun getAllDdata(): List<DataFile>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFile(data: DataFile)

    @Delete
    suspend fun deleteFile(data: DataFile)
}