package com.soldatov.covid.data.models

data class CovidCountryInfo(
    val country: String,
    val lat: String,
    val long: String,
    val confirmed: String,
    val deaths: String,
    val recovered: String,
)
