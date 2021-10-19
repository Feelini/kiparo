package com.soldatov.data.models.filter

data class TotalQualitiesData(
    val page: Int,
    val search: String,
    val hasMore: Boolean,
    val perPage: Int,
    val items: List<QualityData>
)
