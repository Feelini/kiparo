package com.soldatov.covid.data.models

data class CovidInfo(
    val lastCheckTimeMilli: Long,
    val lastCheckTimeText: String,
    val china: ChinaInfo
)