package com.soldatov.covid.domain.models

import java.util.*

data class DomainCovidInfo(
    val lastCheckTime: Date,
    val totalConfirmed: Int,
    val totalDeaths: Int,
    val totalRecovered: Int,
    val countryInfoList: List<DomainCountryInfo>
)