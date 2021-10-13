package com.soldatov.domain.models

data class FilmsList(
    val totalResults: Int,
    val films: List<FilmInfo>
)
