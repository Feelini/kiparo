package com.soldatov.data.models.film

import com.soldatov.data.models.ErrorData

data class SliderResponse(
    val status: String,
    val error: ErrorData,
    val data: TotalFilmsData
)
