package com.soldatov.data.api

import com.soldatov.domain.models.DomainFilmSliderInfo

sealed class FilmsSliderResult{
    class Success(val data: List<DomainFilmSliderInfo>): FilmsSliderResult()
    class Error(val message: String): FilmsSliderResult()
    object Loading: FilmsSliderResult()
}
