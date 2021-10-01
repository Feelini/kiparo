package com.soldatov.data.models

data class SliderResponse(
    val status: String,
    val error: ErrorData,
    val data: TotalFilmsData
)
