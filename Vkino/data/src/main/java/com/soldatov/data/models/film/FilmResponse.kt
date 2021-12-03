package com.soldatov.data.models.film

import com.soldatov.data.models.ErrorData

data class FilmResponse(
    val status: String,
    val error: ErrorData,
    val data: FilmData
)
