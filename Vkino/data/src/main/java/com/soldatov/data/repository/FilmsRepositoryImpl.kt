package com.soldatov.data.repository

import android.util.Log
import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.data.models.Response
import com.soldatov.domain.models.DomainTopSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class FilmsRepositoryImpl(private val placeHolderApi: PlaceHolderApi): FilmsRepository {
    override suspend fun getTopSliderInfo(): DomainTopSliderInfo {
        val topSliderInfo = placeHolderApi.getTopSliderInfo()
        return topSliderInfo.toDomain()
    }

    fun Response.toDomain(): DomainTopSliderInfo {
        Log.d("TAG", this.toString())
        return DomainTopSliderInfo(this.data.films[0].film_id)
    }
}