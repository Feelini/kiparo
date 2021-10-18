package com.soldatov.data.models.filter

data class TotalCategoriesData(
    val page: Int,
    val hasMore: Boolean,
    val items: List<CategoryData>
)
