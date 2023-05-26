package com.example.pdfreader.view.callback

import com.example.pdfreader.database.DataFile


interface ICallbackRecent {
    fun callbackRecent(tag : TagAllFile , data : DataFile)
}
enum class TagRecent{

}