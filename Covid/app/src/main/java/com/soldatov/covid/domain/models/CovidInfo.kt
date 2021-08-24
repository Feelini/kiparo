package com.soldatov.covid.domain.models

data class CovidInfo(
    var lastCheckTimeMilli: String? = null,
    var lastCheckTimeText: String? = null,
    var china: ArrayList<CovidCountryInfo> = ArrayList(),
)