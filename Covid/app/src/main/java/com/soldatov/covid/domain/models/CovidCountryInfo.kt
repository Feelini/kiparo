package com.soldatov.covid.domain.models

data class CovidCountryInfo(
    val country: String? = null,
    val lat: String? = null,
    val long: String? = null,
    val confirmed: String? = null,
    val deaths: String? = null,
    val recovered: String? = null,
)
