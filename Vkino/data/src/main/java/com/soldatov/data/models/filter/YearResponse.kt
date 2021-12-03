package com.soldatov.data.models.filter

import com.soldatov.data.models.ErrorData
import com.soldatov.data.models.film.YearData

data class YearResponse (
    val status: String,
    val error: ErrorData,
    val data: YearData
)