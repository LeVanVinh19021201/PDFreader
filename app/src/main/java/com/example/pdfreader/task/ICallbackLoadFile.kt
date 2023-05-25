package com.example.pdfreader.task

import com.example.pdfreader.database.DataFile
import java.io.File

interface ICallbackLoadFile {
    fun callbackLoadFile(tag :TagLoadfile, data: ArrayList<DataFile>)
}
enum class TagLoadfile{
    LOAD_FILE_SUCCESS
}