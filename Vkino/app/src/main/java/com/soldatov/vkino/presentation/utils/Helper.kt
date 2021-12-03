package com.soldatov.vkino.presentation.utils

import com.soldatov.domain.models.FilmInfo

fun listToString(list: List<String>): String {
    var result = ""
    list.forEach {
        result += it
        result += ", "
    }
    return if (result.length > 2) {
        result.substring(0, result.length - 2)
    } else {
        ""
    }
}

fun getFilmTitle(film: FilmInfo?): String {
    return if (film?.title != null) {
        if (film.year != null) {
            "${film.title} (${film.year})"
        } else film.title!!
    } else ""
}
