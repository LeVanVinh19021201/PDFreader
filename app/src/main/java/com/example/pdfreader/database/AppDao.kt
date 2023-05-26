package com.example.pdfreader.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppDao {
    @Query("SELECT * FROM file_pdf_table ORDER BY id ASC")
    suspend fun getAllDdata(): List<DataFile>

    @Query("SELECT * FROM file_pdf_table WHERE isFavourite =1")
    suspend fun getDataFavoutite(): List<DataFile>

    @Query("SELECT * FROM file_pdf_table WHERE isRecentFile =1")
    suspend fun getDataRecent(): List<DataFile>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFile(data: DataFile)

    @Delete
    suspend fun deleteFile(data: DataFile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFile(data: DataFile)
}