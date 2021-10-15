package com.soldatov.data.api.request_status

import com.soldatov.domain.models.Years

sealed class YearsResult{
    class Success(val data: Years): YearsResult()
    class Error(val message: String): YearsResult()
    object Loading: YearsResult()
}
