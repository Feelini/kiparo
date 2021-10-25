package com.soldatov.domain.models.profile

data class RegisterData(
    val login: String,
    val email: String,
    val password: String,
    val repeatPassword: String
)
