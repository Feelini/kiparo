package com.soldatov.data.models.favorite

import com.soldatov.data.models.film.FilmData

data class TotalFavoriteData(
    val page: Int,
    val totalRows: Int,
    val filmsPerPage: Int,
    val films: List<FilmData>
)
