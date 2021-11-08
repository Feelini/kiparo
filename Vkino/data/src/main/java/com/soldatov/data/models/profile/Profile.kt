package com.soldatov.data.models.profile

data class Profile(
    val ID: Long,
    val login: String,
    val email: String,
    val pendingEmail: String?,
    val firstName: String?,
    val lastName: String?,
    val about: String?,
    val gender: String?,
    val fullName: String,
    val birthday: String?,
    val image: String
)
