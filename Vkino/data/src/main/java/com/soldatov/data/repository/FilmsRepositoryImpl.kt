package com.soldatov.data.repository

import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.data.models.FilmData
import com.soldatov.data.models.TranslationData
import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.models.SearchData
import com.soldatov.domain.repository.FilmsRepository
import java.lang.Exception

const val FILM_HOME_MODE = "com.soldatov.vkino.data.repository.FILM_HOME_MODE"
const val FILM_SLIDER_MODE = "com.soldatov.vkino.data.repository.FILM_SLIDER_MODE"
const val FILM_SEARCH_MODE = "com.soldatov.vkino.data.repository.FILM_SEARCH_MODE"

class FilmsRepositoryImpl(private val placeHolderApi: PlaceHolderApi) : FilmsRepository {

    private lateinit var lastSlider: List<FilmInfo>
    private lateinit var homePageFilms: List<FilmInfo>
    private lateinit var searchFilms: List<FilmInfo>

    override suspend fun getTopSliderInfo(): List<FilmInfo> {
        val topSliderInfo = placeHolderApi.getTopSliderInfo()
        lastSlider = topSliderInfo.data.films.map { it.toDomain() }
        return lastSlider
    }

    override suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmInfo> {
        val similarFilms = placeHolderApi.getSimilarFilmsInfo(filmId)
        lastSlider = similarFilms.data.films.map { it.toDomain() }
        return lastSlider
    }

    override suspend fun getHomePageFilms(): List<FilmInfo> {
        val homePageFilmsInfo = placeHolderApi.getHomePageFilms()
        homePageFilms = homePageFilmsInfo.data.films.map { it.toDomain() }
        return homePageFilms
    }

    override suspend fun getById(filmId: Long, mode: String): FilmInfo {
        return try {
            when (mode) {
                FILM_SLIDER_MODE -> {
                    lastSlider.filter { it.filmId == filmId }[0]
                }
                FILM_HOME_MODE -> {
                    homePageFilms.filter { it.filmId == filmId }[0]
                }
                else -> {
                    searchFilms.filter { it.filmId == filmId }[0]
                }
            }
        } catch (e: Exception){
            val filmInfo = placeHolderApi.getFilmById(filmId)
            filmInfo.data.toDomain()
        }
    }

    override suspend fun getSearchFilms(searchQuery: String): SearchData {
        val searchFilmsInfo = placeHolderApi.getSearchFilmsInfo(searchQuery)
        searchFilms = searchFilmsInfo.data.films.map { it.toDomain() }
        return SearchData(searchFilmsInfo.data.totalRows, searchFilms)
    }

    private fun FilmData.toDomain(): FilmInfo {
        return FilmInfo(
            filmId = film_id,
            title = getFilmName(this.ru_title, this.en_title, this.orig_title),
            poster = poster ?: "",
            rating = rating,
            category = getCategoryName(cat_id),
            genres = genres.map { it.name },
            year = year,
            qualities = qualities.map { it.name },
            translations = getTranslations(translations),
            countries = countries.map { it.name },
            duration = duration ?: "",
            actors = actors.map { it.name },
            composers = composers.map { it.name },
            directors = directors.map { it.name },
            description = description ?: "",
            iframeSrc = iframe_src,
            trailer = trailer ?: ""
        )
    }

    private fun getFilmName(ruTitle: String?, enTitle: String?, origTitle: String?): String {
        return ruTitle ?: enTitle ?: origTitle ?: ""
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

    private fun getTranslations(translations: List<TranslationData>): List<String>{
        val result = ArrayList<String>()
        translations.forEach {
            if (it.title != null){
                result.add(it.title)
            }
        }
        return result
    }
}