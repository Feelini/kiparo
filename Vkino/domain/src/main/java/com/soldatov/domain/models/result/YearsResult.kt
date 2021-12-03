package com.soldatov.domain.models.result

import com.soldatov.domain.models.Years

sealed class YearsResult{
    class Success(val data: Years): YearsResult()
    class Error(val message: String): YearsResult()
    object Loading: YearsResult()
}
