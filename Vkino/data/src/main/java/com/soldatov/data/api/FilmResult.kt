package com.soldatov.data.api

import com.soldatov.domain.models.FilmSliderInfo

sealed class FilmResult{
    class Success(val data: FilmSliderInfo): FilmResult()
    class Error(val message: String): FilmResult()
    object Loading: FilmResult()
}
