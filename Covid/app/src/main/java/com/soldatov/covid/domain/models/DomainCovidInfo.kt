package com.soldatov.covid.domain.models

data class DomainCovidInfo(
    val lastCheckTimeMilli: Long,
    val totalConfirmed: Int,
    val totalDeaths: Int,
    val totalRecovered: Int,
    val countryInfoList: List<DomainCountryInfo>
)