package com.example.pdfreader.view.viewmodel

import com.example.pdfreader.database.AppDao
import com.example.pdfreader.database.DataFile
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AppRepository @Inject constructor(private val dao: AppDao) {
    suspend fun getAllData() = flow {
        emit(dao.getAllDdata())
    }

    suspend fun getDataFavoutite() = flow {
        emit(dao.getDataFavoutite())
    }

    suspend fun getDataRecent() = flow {
        emit(dao.getDataRecent())
    }

    suspend fun addFile(data :DataFile){
        return dao.addFile(data)
    }

    suspend fun deleteFile(data :DataFile){
        return dao.deleteFile(data)
    }

    suspend fun updateFile(data :DataFile){
        return dao.updateFile(data)
    }
}