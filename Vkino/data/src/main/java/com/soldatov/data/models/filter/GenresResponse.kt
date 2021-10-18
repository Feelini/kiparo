package com.soldatov.data.models.filter

import com.soldatov.data.models.ErrorData

data class GenresResponse(
    val status: String,
    val error: ErrorData,
    val data: TotalGenresData
)
