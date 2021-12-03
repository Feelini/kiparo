package com.soldatov.domain.models.profile

sealed class RegisterResult{
    object Success : RegisterResult()
    class Error(val message: String): RegisterResult()
}
