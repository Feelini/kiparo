package com.soldatov.covid.data.models

data class ChinaInfo(
    val totalConfirmed: Int,
    val totalDeaths: Int,
    val totalRecovered: Int,
    val data: List<CovidCountryInfo>
)
