package com.farhanadi.gamezen.data

sealed class ResultData<out T: Any?> {
    object Loading : ResultData<Nothing>()
    data class Success<out T: Any>(val data: T) : ResultData<T>()
    data class Error(val message: String) : ResultData<Nothing>()
}
