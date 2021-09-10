package com.soldatov.data.models

data class Response(
    val status: String,
    val error: ErrorData,
    val data: TotalFilmsData
)
