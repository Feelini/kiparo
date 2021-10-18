package com.soldatov.domain.models.result

import com.soldatov.domain.models.FilmInfo

sealed class FilmsSliderResult{
    class Success(val data: List<FilmInfo>): FilmsSliderResult()
    class Error(val message: String): FilmsSliderResult()
    object Loading: FilmsSliderResult()
}
