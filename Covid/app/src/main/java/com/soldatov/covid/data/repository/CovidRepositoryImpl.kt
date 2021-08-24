package com.soldatov.covid.data.repository

import com.soldatov.covid.domain.models.CovidInfo
import com.soldatov.covid.domain.repository.CovidRepository

import com.soldatov.covid.data.api.NetworkService
import retrofit2.Call
import java.util.concurrent.CompletableFuture


class CovidRepositoryImpl : CovidRepository {

    override fun getCovidInfo(): CompletableFuture<Call<CovidInfo>> {
        return CompletableFuture.supplyAsync {
            NetworkService.getInstance()?.getJSONApi()?.getCovidInfo()
        }
    }
}