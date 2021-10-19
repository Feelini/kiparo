package com.soldatov.data.repository

import com.soldatov.data.models.film.FilmData
import com.soldatov.data.models.film.TranslationData
import com.soldatov.data.models.film.YearData
import com.soldatov.data.models.filter.*
import com.soldatov.data.models.home.HomePageFilmsParams
import com.soldatov.domain.models.*

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
        genres = items.map { Genre(it.ID, it.name, false) }
    )
}

fun TotalCategoriesData.toDomain(): List<Category>{
    return items.map { Category(it.ID, it.namePlural, false) }
}

fun YearData.toDomain(): Years{
    return Years(
        min = min,
        max = max
    )
}

fun TotalCountiesData.toDomain(): CountriesList{
    return CountriesList(
        hasMore = hasMore,
        countries = items.map { Country(it.ID, it.name) }
    )
}

fun TotalActorsData.toDomain(): ActorsList{
    return ActorsList(
        hasMore = hasMore,
        actors = items.map { Actor(it.ID, it.name) }
    )
}

fun TotalQualitiesData.toDomain(): List<Quality>{
    return items.map { Quality(it.ID, it.name, false) }
}

fun FilterParams.toData(): HomePageFilmsParams{
    var result = ""
    if (chosenCategories.isNotEmpty()){
        chosenCategories.forEach {
            result += "${it.id},"
        }
        result = result.substring(0, result.length - 1)
    }
    val categories = if (result.isEmpty()) null else result
    result = ""
    if (chosenGenres.isNotEmpty()){
        chosenGenres.forEach {
            result += "${it.id},"
        }
        result = result.substring(0, result.length - 1)
    }
    val genres = if (result.isEmpty()) null else result
    result = ""
    if (chosenCountries.isNotEmpty()){
        chosenCountries.forEach {
            result += "${it.id},"
        }
        result = result.substring(0, result.length - 1)
    }
    val countries = if (result.isEmpty()) null else result
    result = ""
    if (chosenActors.isNotEmpty()){
        chosenActors.forEach {
            result += "${it.id},"
        }
        result = result.substring(0, result.length - 1)
    }
    val actors = if (result.isEmpty()) null else result
    result = ""
    if (chosenQualities.isNotEmpty()){
        chosenQualities.forEach {
            result += "${it.id},"
        }
        result = result.substring(0, result.length - 1)
    }
    val qualities = if (result.isEmpty()) null else result
    val years = if (chosenYears == null) null else "${chosenYears!!.min},${chosenYears!!.max}"
    return HomePageFilmsParams(
        chosenCategories = categories,
        chosenGenres = genres,
        chosenCountries = countries,
        chosenActors = actors,
        chosenQualities = qualities,
        chosenYears = years,
        page = page
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