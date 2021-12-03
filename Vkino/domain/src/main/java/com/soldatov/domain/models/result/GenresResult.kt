package com.soldatov.domain.models.result

import com.soldatov.domain.models.GenresList

sealed class GenresResult {
    class Success(val data: GenresList): GenresResult()
    class Error(val message: String): GenresResult()
    object Loading: GenresResult()
}