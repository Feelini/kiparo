package com.soldatov.domain.models.result

import com.soldatov.domain.models.CountriesList

sealed class CountriesResult{
    class Success(val data: CountriesList): CountriesResult()
    class Error(val message: String): CountriesResult()
    object Loading: CountriesResult()
}
