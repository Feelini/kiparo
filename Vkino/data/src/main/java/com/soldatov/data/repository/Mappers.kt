package com.soldatov.data.repository

import com.soldatov.data.models.FilmData
import com.soldatov.data.models.TotalGenresData
import com.soldatov.data.models.TranslationData
import com.soldatov.data.models.YearData
import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.models.Genre
import com.soldatov.domain.models.GenresList
import com.soldatov.domain.models.Years

fun FilmData.toDomain(): FilmInfo {
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

fun TotalGenresData.toDomain(): GenresList{
    return GenresList(
        hasMore = hasMore,
        genres = items.map { Genre(it.id, it.name, false) }
    )
}

fun YearData.toDomain(): Years{
    return Years(
        min = min,
        max = max
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