package com.soldatov.covid.domain.repository

import com.soldatov.covid.domain.models.CovidInfo
import retrofit2.Call
import java.util.concurrent.CompletableFuture

interface CovidRepository {
    fun getCovidInfo(): CompletableFuture<Call<CovidInfo>>
}