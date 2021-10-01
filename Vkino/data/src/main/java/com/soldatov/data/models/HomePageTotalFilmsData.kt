package com.soldatov.data.models

data class HomePageTotalFilmsData(
    val page: Int,
    val totalRows: Int,
    val filmsPerPage: Int,
    val films: List<FilmData>
)