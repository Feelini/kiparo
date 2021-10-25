package com.soldatov.domain.models.profile

sealed class RegisterResult{
    class Success(val token: String): RegisterResult()
    class Error(val message: String): RegisterResult()
}
