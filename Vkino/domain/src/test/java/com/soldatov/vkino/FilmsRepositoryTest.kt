package com.soldatov.vkino

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.models.FilmsList
import com.soldatov.domain.repository.FilmsRepository

class FilmsRepositoryTest: FilmsRepository {
    override suspend fun getTopSliderInfo(): List<FilmInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getHomePageFilms(page: Int): FilmsList {
        TODO("Not yet implemented")
    }

    override suspend fun getSimilarFilmsInfo(filmId: Long): List<FilmInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(filmId: Long, mode: String): FilmInfo {
        val films = listOf(
            FilmInfo(
                filmId = 1,
                title = "Title",
                poster = "Poster",
                rating = 4.0,
                category = "Category",
                genres = emptyList(),
                year = 2020,
                qualities = emptyList(),
                translations = emptyList(),
                countries = emptyList(),
                duration = "",
                actors = emptyList(),
                composers = emptyList(),
                directors = emptyList(),
                description = "Description",
                iframeSrc = "",
                trailer = ""
            ),
            FilmInfo(
                filmId = 2,
                title = "Title",
                poster = "Poster",
                rating = 4.0,
                category = "Category",
                genres = emptyList(),
                year = 2020,
                qualities = emptyList(),
                translations = emptyList(),
                countries = emptyList(),
                duration = "",
                actors = emptyList(),
                composers = emptyList(),
                directors = emptyList(),
                description = "Description",
                iframeSrc = "",
                trailer = ""
            )
        )
        return films.filter { it.filmId == filmId }[0]
    }

    override suspend fun getSearchFilms(searchQuery: String, page: Int): FilmsList {
        TODO("Not yet implemented")
    }
}