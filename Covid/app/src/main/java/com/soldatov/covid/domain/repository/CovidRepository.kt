package com.soldatov.covid.domain.repository

import com.soldatov.covid.domain.models.CovidInfo

interface CovidRepository {
    suspend fun getCovidInfo(): CovidInfo
}