package com.soldatov.domain.models.favorite

import com.soldatov.domain.models.FilmInfo

sealed class FavoriteResult{
    class Success(val data: List<FilmInfo>, val hasMore: Boolean): FavoriteResult()
    class Error(val message: String): FavoriteResult()
}
