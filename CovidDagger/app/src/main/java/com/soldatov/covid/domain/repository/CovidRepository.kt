package com.soldatov.covid.domain.repository

import com.soldatov.covid.domain.models.DomainCovidInfo

interface CovidRepository {
    suspend fun getCovidInfo(): DomainCovidInfo
}