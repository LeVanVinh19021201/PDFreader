package com.example.pdfreader.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfreader.database.DataFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repo: AppRepository) : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state = _state

    fun getALlData() {
        viewModelScope.launch {
            _state.value = State.response(status = State.Status.GET_ALl_LOADING)
            repo.getAllData().collect {
                if (it != null && it.isNotEmpty()) {
                    _state.value = State.response(data = it, status = State.Status.GET_ALl_SUCCESS)
                } else {
                    _state.value = State.response(data = null, status = State.Status.GET_ALl_FAIL)
                }
            }
        }
    }

    fun getDataRecent() {
        viewModelScope.launch {
            _state.value = State.response(status = State.Status.GET_ALl_LOADING)
            repo.getDataRecent().collect {
                if (it != null && it.isNotEmpty()) {
                    _state.value = State.response(convertListRecent(it as ArrayList<DataFile>?), status = State.Status.GET_ALl_SUCCESS)
                } else {
                    _state.value = State.response(data = null, status = State.Status.GET_ALl_FAIL)
                }
            }
        }
    }

    fun getDataFavoutite() {
        viewModelScope.launch() {
            _state.value = State.response(status = State.Status.GET_ALl_LOADING)
            repo.getDataFavoutite().collect {
                if (it != null && it.isNotEmpty()) {
                    _state.value = State.response(
                        data = convertListFavourite(it as ArrayList<DataFile>?),
                        status = State.Status.GET_ALl_SUCCESS
                    )
                } else {
                    _state.value = State.response(data = null, status = State.Status.GET_ALl_FAIL)
                }
            }
        }
    }

    fun convertListFavourite(data: ArrayList<DataFile>?): ArrayList<DataFile> {
        val dataFavourite = ArrayList<DataFile>()
        data?.forEach {
            if (it.isFavourite == 1) {
                dataFavourite.add(it)
            }
        }
        return dataFavourite
    }


    fun convertListRecent(data: ArrayList<DataFile>?): ArrayList<DataFile> {
        val dataFavourite = ArrayList<DataFile>()
        data?.forEach {
            if (it.isRecentFile == 1) {
                dataFavourite.add(it)
            }
        }
        return dataFavourite
    }

    fun updateDataFile(data: DataFile) {
        viewModelScope.launch {
            repo.updateFile(data)
        }
    }

    fun addFile(data: DataFile) {
        viewModelScope.launch {
            repo.addFile(data)
        }
    }
}