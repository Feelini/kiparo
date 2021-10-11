package com.soldatov.data.models

data class SearchResponse(
    val status: String,
    val error: ErrorData,
    val data: TotalSearchData
)
