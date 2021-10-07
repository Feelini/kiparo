package com.soldatov.data.models

data class FilmResponse(
    val status: String,
    val error: ErrorData,
    val data: FilmData
)
