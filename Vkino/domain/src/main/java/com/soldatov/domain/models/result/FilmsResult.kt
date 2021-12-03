package com.soldatov.domain.models.result

import com.soldatov.domain.models.FilmsList

sealed class FilmsResult{
    class Success(val data: FilmsList): FilmsResult()
    class Error(val message: String): FilmsResult()
    object Loading: FilmsResult()
}
