package com.soldatov.data.models.search

import com.soldatov.data.models.film.FilmData

data class TotalSearchData(
    val page: Int,
    val totalRows: Int,
    val filmsPerPage: Int,
    val films: List<FilmData>
)
