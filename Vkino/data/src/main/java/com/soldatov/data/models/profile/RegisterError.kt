package com.soldatov.data.models.profile

import com.soldatov.data.models.ErrorData

data class RegisterError(
    val status: String,
    val error: ErrorData
)
