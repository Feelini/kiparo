package com.soldatov.data.utils

import com.soldatov.domain.models.DomainTaxiInfo

sealed class Result{
    class Success(val data: List<DomainTaxiInfo>): Result()
    class Error(val message: String): Result()
    object Loading: Result()
}
