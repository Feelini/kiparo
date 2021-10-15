package com.soldatov.data.models

data class TotalGenresData(
    val page: Int,
    val search: String,
    val hasMore: Boolean,
    val perPage: Int,
    val items: List<GenreData>
)
