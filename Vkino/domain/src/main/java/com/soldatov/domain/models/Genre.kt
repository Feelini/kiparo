package com.soldatov.domain.models

data class Genre(
    val id: Long,
    val name: String,
    var isChecked: Boolean
)
