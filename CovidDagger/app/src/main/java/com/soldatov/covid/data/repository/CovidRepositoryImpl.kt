package com.soldatov.covid.data.repository

import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.models.CovidCountryInfo
import com.soldatov.covid.data.models.CovidInfo
import com.soldatov.covid.domain.models.DomainCountryInfo
import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.domain.repository.CovidRepository
import java.util.*

class CovidRepositoryImpl(private val apiHelper: ApiHelper) : CovidRepository {

    override suspend fun getCovidInfo(): DomainCovidInfo {
        val covidInfo = apiHelper.getCovidInfo()
        return covidInfo.toDomain()
    }

    private fun CovidCountryInfo.toDomain(): DomainCountryInfo {
        return DomainCountryInfo(this.country, this.lat, this.long, this.confirmed)
    }

    private fun CovidInfo.toDomain(): DomainCovidInfo{
        val domainCountryInfoList = this.china.data
            .filter { it.lat != null && it.long != null }
            .map { it.toDomain() }

        return DomainCovidInfo(
            Date(this.lastCheckTimeMilli),
            this.china.totalConfirmed,
            this.china.totalDeaths,
            this.china.totalRecovered,
            domainCountryInfoList
        )
    }
}