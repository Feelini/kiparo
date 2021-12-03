package com.soldatov.data.models.home

import com.soldatov.data.models.ErrorData

data class HomePageFilmsResponse(
    val status: String,
    val error: ErrorData,
    val data: HomePageTotalFilmsData
)
