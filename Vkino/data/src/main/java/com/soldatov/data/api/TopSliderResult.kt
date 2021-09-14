package com.soldatov.data.api

import com.soldatov.domain.models.DomainTopSliderInfo

sealed class TopSliderResult{
    class Success(val data: List<DomainTopSliderInfo>): TopSliderResult()
    class Error(val message: String): TopSliderResult()
    object Loading: TopSliderResult()
}
