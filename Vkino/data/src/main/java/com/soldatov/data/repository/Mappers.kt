package com.soldatov.data.repository

import com.soldatov.data.models.film.FilmData
import com.soldatov.data.models.film.TranslationData
import com.soldatov.data.models.film.YearData
import com.soldatov.data.models.filter.*
import com.soldatov.data.models.home.HomePageFilmsParams
import com.soldatov.data.models.profile.LoginUser
import com.soldatov.data.models.profile.Profile
import com.soldatov.data.models.profile.RegisterRequest
import com.soldatov.data.models.profile.UserInfoTotalData
import com.soldatov.domain.models.*
import com.soldatov.domain.models.favourite.Categories
import com.soldatov.domain.models.profile.Gender
import com.soldatov.domain.models.profile.LoginData
import com.soldatov.domain.models.profile.RegisterData
import com.soldatov.domain.models.profile.UserInfo

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
        page = page,
        orderBy = orderBy,
        order = order
    )
}

fun LoginData.toData(): LoginUser{
    return LoginUser(
        login = login,
        password = password
    )
}

fun RegisterData.toData(): RegisterRequest{
    return RegisterRequest(
        login = login,
        email = email,
        password = password,
        repeatPassword = repeatPassword
    )
}

fun CategoryData.toDomain(): Categories{
    return Categories.getCategoryByName(this.namePlural)
}

fun UserInfoTotalData.toDomain(): UserInfo{
    return UserInfo(
        id = profile.ID,
        login = profile.login,
        email = profile.email,
        pendingEmail = profile.pendingEmail ?: "",
        firstName = profile.firstName ?: "",
        lastName = profile.lastName ?: "",
        about = profile.about ?: "",
        gender = if (profile.gender == "f") Gender.FEMALE else Gender.MALE,
        fullName = profile.fullName,
        birthday = profile.birthday ?: "",
        image = profile.image
    )
}

fun UserInfo.toData(): Profile{
    return Profile(
        ID = id,
        login = login,
        email = email,
        pendingEmail = pendingEmail,
        firstName = firstName,
        lastName = lastName,
        about = about,
        gender = if (gender.name == Gender.FEMALE.name) "f" else "m",
        fullName = fullName,
        birthday = birthday,
        image = image
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