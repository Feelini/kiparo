package com.soldatov.data.models.home

import com.soldatov.data.models.film.FilmData

data class HomePageTotalFilmsData(
    val page: Int,
    val totalRows: Int,
    val filmsPerPage: Int,
    val films: List<FilmData>
)
