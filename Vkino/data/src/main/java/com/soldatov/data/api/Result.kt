package com.soldatov.data.api

sealed class Result{
    class Success(val data: Any): Result()
    class Error(val message: String): Result()
    object Loading: Result()
}
