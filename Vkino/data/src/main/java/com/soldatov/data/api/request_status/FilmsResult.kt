package com.soldatov.data.api.request_status

import com.soldatov.domain.models.FilmsList

sealed class FilmsResult{
    class Success(val data: FilmsList): FilmsResult()
    class Error(val message: String): FilmsResult()
    object Loading: FilmsResult()
}
