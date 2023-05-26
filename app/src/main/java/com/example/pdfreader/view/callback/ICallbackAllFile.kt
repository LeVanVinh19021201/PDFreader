package com.example.pdfreader.view.callback

import com.example.pdfreader.database.DataFile

interface ICallbackAllFile {
    fun callbackALlFile(tag : TagAllFile , data :DataFile)
}
enum class TagAllFile{
    ON_CLICK_FAVOURITE,
    ON_CLICK_OPEN_FILE
}