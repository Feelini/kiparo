package com.soldatov.domain.models

data class Category(
    val id: Long,
    val name: String,
    var isChecked: Boolean
)
