package com.soldatov.covid.data.api

class ApiHelper(private val apiService: JSONPlaceHolderApi) {

    suspend fun getCovidInfo() = apiService.getCovidInfo()
}