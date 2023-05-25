package com.example.pdfreader.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "file_pdf_table")
@Parcelize
data class DataFile(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val path: String = "",
    val isFavourite: Boolean = false,
    val timeClickRecent: Long = 0L,
    val timeClickFavourite: Long = 0L,
    val timeOpenFile: Long = 0L,
) : Parcelable