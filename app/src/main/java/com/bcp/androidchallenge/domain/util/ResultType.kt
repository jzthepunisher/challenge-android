package com.bcp.androidchallenge.domain.util

sealed class ResultType<out T : Any> {

    data class Success<out T : Any>(val data: T) : ResultType<T>()
    data class Error(val exception: Exception) : ResultType<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}