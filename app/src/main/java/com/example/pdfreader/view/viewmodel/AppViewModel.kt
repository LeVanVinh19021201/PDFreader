package com.example.pdfreader.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repo : AppRepository) :ViewModel(){
    private val _state = MutableLiveData<State>()
    val state = _state

    fun getALlData(){
        viewModelScope.launch {
            _state.value = State.response(status = State.Status.GET_ALl_LOADING)
            repo.getAllData().collect{
                if(it!=null && it.isNotEmpty()){
                    _state.value = State.response(data = it, status = State.Status.GET_ALl_SUCCESS)
                }else{
                    _state.value = State.response(data = null, status = State.Status.GET_ALl_FAIL)
                }
            }
        }
    }
}