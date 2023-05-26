package com.example.pdfreader.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "file_pdf_table")
@Parcelize
data class DataFile(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var path: String = "",
    var isFavourite: Int = 0,
    var isRecentFile: Int = 0,
    var timeOpenRecent: Long = 0L,
    var timeClickFavourite: Long = 0L,
    var timeOpenFile: Long = 0L,
) : Parcelable