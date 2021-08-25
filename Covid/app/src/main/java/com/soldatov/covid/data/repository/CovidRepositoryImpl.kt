package com.soldatov.covid.data.repository

import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.domain.repository.CovidRepository

class CovidRepositoryImpl(private val apiHelper: ApiHelper) : CovidRepository {

    override suspend fun getCovidInfo() = apiHelper.getCovidInfo()
}