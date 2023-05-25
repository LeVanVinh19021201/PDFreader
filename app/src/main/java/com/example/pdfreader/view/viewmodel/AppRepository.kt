package com.example.pdfreader.view.viewmodel

import com.example.pdfreader.database.AppDao
import com.example.pdfreader.database.DataFile
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AppRepository @Inject constructor(private val dao: AppDao) {
    suspend fun getAllData() = flow {
        emit(dao.getAllDdata())
    }

    suspend fun addFile(data :DataFile){
        return dao.addFile(data)
    }

    suspend fun deleteFile(data :DataFile){
        return dao.addFile(data)
    }
}