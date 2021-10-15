package com.soldatov.data.models

data class YearResponse (
    val status: String,
    val error: ErrorData,
    val data: YearData
)