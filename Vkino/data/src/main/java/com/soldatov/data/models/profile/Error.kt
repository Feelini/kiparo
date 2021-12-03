package com.soldatov.data.models.profile

import com.soldatov.data.models.ErrorData

data class Error(
    val status: String,
    val error: ErrorData
)
