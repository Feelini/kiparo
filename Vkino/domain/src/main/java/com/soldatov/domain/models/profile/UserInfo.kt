package com.soldatov.domain.models.profile

data class UserInfo(
    val id: Long,
    val login: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val about: String,
    val gender: Gender,
    val fullName: String,
    val birthday: String
)
