package com.soldatov.covid.domain.models

data class CovidInfo(
    var lastCheckTimeMilli: String,
    var lastCheckTimeText: String,
    var china: ChinaInfo
)