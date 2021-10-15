package com.soldatov.data.models

data class GenresResponse(
    val status: String,
    val error: ErrorData,
    val data: TotalGenresData
)
