package com.soldatov.data.api

import com.soldatov.domain.models.FilmSliderInfo

sealed class FilmsSliderResult{
    class Success(val data: List<FilmSliderInfo>): FilmsSliderResult()
    class Error(val message: String): FilmsSliderResult()
    object Loading: FilmsSliderResult()
}
