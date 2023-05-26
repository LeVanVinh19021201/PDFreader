package com.example.pdfreader.view.viewmodel

data class State(
    val data: Any?,
    val status: Status,
    val msg: String? = null,
) {
    companion object {
        fun response(data: Any?=null, status: Status, msg: String? = null) =
            State(data, status, msg)
    }

    enum class Status {
       GET_ALl_SUCCESS,
       GET_ALl_LOADING,
       GET_ALl_FAIL,

        GET_RECENT_SUCCESS,
        GET_RECENT_LOADING,
        GET_RECENT_FAIL,

        GET_FAVOURITE_SUCCESS,
        GET_FAVOURITE_LOADING,
        GET_FAVOURITE_FAIL
    }
}