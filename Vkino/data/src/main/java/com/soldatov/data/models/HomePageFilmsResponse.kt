package com.soldatov.data.models

data class HomePageFilmsResponse(
    val status: String,
    val error: ErrorData,
    val data: HomePageTotalFilmsData
)
