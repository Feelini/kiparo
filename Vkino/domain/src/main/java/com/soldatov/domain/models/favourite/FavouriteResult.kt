package com.soldatov.domain.models.favourite

import com.soldatov.domain.models.FilmInfo

sealed class FavouriteResult{
    class Success(val data: List<FilmInfo>, val hasMore: Boolean): FavouriteResult()
    class Error(val message: String): FavouriteResult()
}
