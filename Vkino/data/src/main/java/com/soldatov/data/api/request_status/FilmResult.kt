package com.soldatov.data.api.request_status

import com.soldatov.domain.models.FilmInfo

sealed class FilmResult{
    class Success(val data: FilmInfo): FilmResult()
    class Error(val message: String): FilmResult()
    object Loading: FilmResult()
}
