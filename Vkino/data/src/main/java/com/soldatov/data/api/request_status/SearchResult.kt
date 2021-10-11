package com.soldatov.data.api.request_status

import com.soldatov.domain.models.SearchData

sealed class SearchResult{
    class Success(val data: SearchData): SearchResult()
    class Error(val message: String): SearchResult()
    object Loading: SearchResult()
}
