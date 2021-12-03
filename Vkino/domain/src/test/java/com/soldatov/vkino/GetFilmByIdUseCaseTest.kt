package com.soldatov.vkino

import com.soldatov.domain.models.FilmInfo
import com.soldatov.domain.usecase.GetFilmByIdUseCase
import org.junit.Assert
import org.junit.Test

class GetFilmByIdUseCaseTest {

    @Test
    suspend fun executeTest() {

        val repository = FilmsRepositoryTest()

        val getFilmByIdUseCase = GetFilmByIdUseCase(repository)
        val result = getFilmByIdUseCase.execute(1, "home")

        Assert.assertEquals(
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
            ), result
        )
    }
}