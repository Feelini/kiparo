package com.soldatov.data.repository

import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.data.models.FilmData
import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

class FilmsRepositoryImpl(private val placeHolderApi: PlaceHolderApi) : FilmsRepository {

    private lateinit var lastSlider: List<DomainFilmSliderInfo>

    override suspend fun getTopSliderInfo(): List<DomainFilmSliderInfo> {
        val topSliderInfo = placeHolderApi.getTopSliderInfo()
        lastSlider = topSliderInfo.data.films.map { it.toDomain() }
        return lastSlider
    }

    override suspend fun getSimilarFilmsInfo(filmId: Long): List<DomainFilmSliderInfo> {
        val similarFilms = placeHolderApi.getSimilarFilmsInfo(filmId)
        lastSlider = similarFilms.data.films.map { it.toDomain() }
        return lastSlider
    }

    override fun getFilmById(filmId: Long): DomainFilmSliderInfo {
        return lastSlider.filter { it.filmId == filmId }[0]
    }

    private fun FilmData.toDomain(): DomainFilmSliderInfo {
        return DomainFilmSliderInfo(
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