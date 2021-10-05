package com.soldatov.data.repository

import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.data.models.FilmData
import com.soldatov.domain.models.FilmSliderInfo
import com.soldatov.domain.repository.FilmsRepository

const val MODE_SLIDER = "slider"
const val MODE_HOME = "home"

class FilmsRepositoryImpl(private val placeHolderApi: PlaceHolderApi) : FilmsRepository {

    private lateinit var lastSlider: List<FilmSliderInfo>
    private lateinit var homePageFilms: List<FilmSliderInfo>

    override suspend fun getTopSliderInfo(): List<FilmSliderInfo> {
        val topSliderInfo = placeHolderApi.getTopSliderInfo()
        lastSlider = topSliderInfo.data.films.map { it.toDomain() }
        return lastSlider
    }

    override suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmSliderInfo> {
        val similarFilms = placeHolderApi.getSimilarFilmsInfo(filmId)
        lastSlider = similarFilms.data.films.map { it.toDomain() }
        return lastSlider
    }

    override suspend fun getHomePageFilms(): List<FilmSliderInfo> {
        val homePageFilmsInfo = placeHolderApi.getHomePageFilms()
        homePageFilms = homePageFilmsInfo.data.films.map { it.toDomain() }
        return homePageFilms
    }

    override fun getById(filmId: Long, mode: String): FilmSliderInfo {
        return if(mode == MODE_SLIDER){
            lastSlider.filter { it.filmId == filmId }[0]
        } else {
            homePageFilms.filter { it.filmId == filmId }[0]
        }
    }

    private fun FilmData.toDomain(): FilmSliderInfo {
        return FilmSliderInfo(
            filmId = film_id,
            title = getFilmName(this.ru_title, this.en_title, this.orig_title),
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

    private fun getFilmName(ruTitle: String?, enTitle: String?, origTitle: String?): String? {
        return ruTitle ?: enTitle ?: origTitle
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