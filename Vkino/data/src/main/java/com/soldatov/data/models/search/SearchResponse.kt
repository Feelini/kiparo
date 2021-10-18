package com.soldatov.data.models.search

import com.soldatov.data.models.ErrorData

data class SearchResponse(
    val status: String,
    val error: ErrorData,
    val data: TotalSearchData
)
