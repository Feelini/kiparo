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
            title = getFilmName(this),
            year = this.year
        )
    }

    private fun getFilmName(filmData: FilmData): String? {
        return filmData.ru_title ?: filmData.en_title ?: filmData.orig_title
    }
}