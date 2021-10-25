package com.soldatov.data.models.profile

data class RegisterRequest(
    val login: String,
    val email: String,
    val password: String,
    val repeatPassword: String
)
