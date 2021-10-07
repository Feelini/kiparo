package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.*
import com.soldatov.data.api.FilmResult
import com.soldatov.data.api.FilmsSliderResult
import com.soldatov.data.repository.MODE_HOME
import com.soldatov.data.repository.MODE_SLIDER
import com.soldatov.domain.usecase.GetFilmByIdUseCase
import com.soldatov.domain.usecase.GetSimilarFilmsUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FilmFragmentViewModel(
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase,
    private val getFilmByIdUseCase: GetFilmByIdUseCase
) : ViewModel() {

    private val filmId = MutableLiveData<Long>()

    val sliderFilmById = Transformations.switchMap(filmId) { id ->
        liveData(Dispatchers.IO) {
            try {
                emit(FilmResult.Success(data = getFilmByIdUseCase.execute(filmId = id, MODE_SLIDER)))
            } catch (exception: Exception) {
                emit(FilmResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    val homeFilmById = Transformations.switchMap(filmId) { id ->
        liveData(Dispatchers.IO) {
            try {
                emit(FilmResult.Success(data = getFilmByIdUseCase.execute(filmId = id, MODE_HOME)))
            } catch (exception: Exception) {
                emit(FilmResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    val similarFilms = Transformations.switchMap(filmId) { id ->
        liveData(Dispatchers.IO) {
            try {
                emit(FilmsSliderResult.Success(data = getSimilarFilmsUseCase.execute(filmId = id)))
            } catch (exception: Exception) {
                emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    fun setFilmId(filmIdNew: Long) {
        filmId.value = filmIdNew
    }
}