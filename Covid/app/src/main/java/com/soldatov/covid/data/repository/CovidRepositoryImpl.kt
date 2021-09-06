package com.soldatov.covid.data.repository

import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.models.CovidInfo
import com.soldatov.covid.domain.models.DomainCountryInfo
import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.domain.repository.CovidRepository

class CovidRepositoryImpl(private val apiHelper: ApiHelper) : CovidRepository {

    override suspend fun getCovidInfo(): DomainCovidInfo {
        val covidInfo = apiHelper.getCovidInfo()
        return mapToDomain(covidInfo)
    }

    private fun mapToDomain(covidInfo: CovidInfo): DomainCovidInfo {
        val domainCountryInfoList = ArrayList<DomainCountryInfo>()
        covidInfo.china.data.forEach {
            if (it.lat != null && it.long != null) {
                val domainCountryInfo = DomainCountryInfo(it.country, it.lat, it.long, it.confirmed)
                domainCountryInfoList.add(domainCountryInfo)
            }
        }

        return DomainCovidInfo(
            covidInfo.lastCheckTimeMilli,
            covidInfo.china.totalConfirmed,
            covidInfo.china.totalDeaths,
            covidInfo.china.totalRecovered,
            domainCountryInfoList
        )
    }
}