package com.soldatov.covid.domain.models

data class ChinaInfo(
    val totalConfirmed: Int,
    val totalDeaths: Int,
    val totalRecovered: Int,
    val data: ArrayList<CovidCountryInfo>
)
