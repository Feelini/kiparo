package com.soldatov.covid.domain.models

data class CovidInfo(
    var lastCheckTimeMilli: Long,
    var lastCheckTimeText: String,
    var china: ChinaInfo
)