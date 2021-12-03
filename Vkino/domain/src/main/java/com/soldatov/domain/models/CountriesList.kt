package com.soldatov.domain.models

data class CountriesList(
    val hasMore: Boolean,
    val countries: List<Country>
)
