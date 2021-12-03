package com.soldatov.data.models.profile

import com.soldatov.data.models.ErrorData

data class LoginResponse(
    val status: String,
    val error: ErrorData,
    val data: LoginTotalData
)
