package com.soldatov.domain.models

data class SearchData(
    val totalResults: Int,
    val films: List<FilmInfo>
)
