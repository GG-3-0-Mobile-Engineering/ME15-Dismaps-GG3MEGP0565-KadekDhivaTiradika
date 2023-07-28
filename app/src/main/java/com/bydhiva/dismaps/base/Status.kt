package com.bydhiva.dismaps.base

sealed class Status<T>(val data: T? = null, val error: Throwable? = null) {
    class Loading<T>: Status<T>()
    class Success<T>(data: T? = null): Status<T>(data = data)
    class Error<T>(error: Throwable? = null): Status<T>(error = error)
}