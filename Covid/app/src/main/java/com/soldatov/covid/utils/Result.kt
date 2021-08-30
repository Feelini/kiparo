package com.soldatov.covid.utils

import com.soldatov.covid.domain.models.CovidInfo

sealed class Result{
    class Success(val data: CovidInfo): Result()
    class Error(val message: String): Result()
    object Loading : Result()
}
