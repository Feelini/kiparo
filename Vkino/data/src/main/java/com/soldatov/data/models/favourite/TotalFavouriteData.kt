package com.soldatov.data.models.favourite

import com.soldatov.data.models.film.FilmData

data class TotalFavouriteData(
    val page: Int,
    val totalRows: Int,
    val filmsPerPage: Int,
    val films: List<FilmData>
)
