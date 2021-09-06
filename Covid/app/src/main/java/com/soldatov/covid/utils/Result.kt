package com.soldatov.covid.utils

import com.soldatov.covid.domain.models.DomainCovidInfo

sealed class Result{
    class Success(val data: DomainCovidInfo): Result()
    class Error(val message: String): Result()
    object Loading : Result()
}
