package com.soldatov.data.models.filter

data class TotalActorsData(
    val page: Int,
    val search: String,
    val hasMore: Boolean,
    val perPage: Int,
    val items: List<ActorData>
)
