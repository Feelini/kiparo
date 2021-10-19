package com.soldatov.data.models.filter

import com.soldatov.data.models.ErrorData

data class ActorsResponse(
    val status: String,
    val error: ErrorData,
    val data: TotalActorsData
)
