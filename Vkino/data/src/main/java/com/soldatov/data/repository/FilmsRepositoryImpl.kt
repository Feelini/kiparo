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
            filmId = film_id,
            title = getFilmName(this),
            poster = poster,
            rating = rating,
            category = getCategoryName(cat_id),
            genres = genres.map { it.name },
            year = year,
            qualities = qualities.map { it.name },
            translations = translations.map { it.title },
            countries = countries.map { it.name },
            duration = duration,
            actors = actors.map { it.name },
            composers = composers.map { it.name },
            directors = directors.map { it.name },
            description = description,
            iframeSrc = iframe_src,
            trailer = trailer
        )
    }

    private fun getFilmName(filmData: FilmData): String? {
        return filmData.ru_title ?: filmData.en_title ?: filmData.orig_title
    }

    private fun getCategoryName(catId: Int): String{
        return when(catId){
            1 -> "Фильмы"
            2 -> "Аниме"
            3 -> "Сериалы"
            4 -> "Сериалы аниме"
            else -> "ТВ шоу"
        }
    }
}