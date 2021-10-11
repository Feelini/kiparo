package com.soldatov.data.api.request_status

import com.soldatov.domain.models.FilmInfo

sealed class FilmsSliderResult{
    class Success(val data: List<FilmInfo>): FilmsSliderResult()
    class Error(val message: String): FilmsSliderResult()
    object Loading: FilmsSliderResult()
}
