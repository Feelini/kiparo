package com.soldatov.data.repository

import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.data.models.FilmData
import com.soldatov.domain.models.DomainTopSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class FilmsRepositoryImpl(private val placeHolderApi: PlaceHolderApi) : FilmsRepository {
    override suspend fun getTopSliderInfo(): List<DomainTopSliderInfo> {
        val topSliderInfo = placeHolderApi.getTopSliderInfo()
        return topSliderInfo.data.films.map { it.toDomain() }
    }

    private fun FilmData.toDomain(): DomainTopSliderInfo {
        return DomainTopSliderInfo(
            filmId = this.film_id,
            genres = this.genres.map { it.name },
            poster = this.poster,
            ruTitle = this.ru_title,
            engTitle = this.en_title,
            origTitle = this.orig_title,
            year = this.year
        )
    }
}