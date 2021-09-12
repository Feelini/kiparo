package com.soldatov.domain.repository

import com.soldatov.domain.models.DomainTopSliderInfo

interface FilmsRepository {
    suspend fun getTopSliderInfo(): List<DomainTopSliderInfo>
}