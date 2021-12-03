package com.soldatov.vkino.presentation.ui.film

import androidx.lifecycle.*
import com.soldatov.domain.models.result.FilmResult
import com.soldatov.domain.models.result.FilmsSliderResult
import com.soldatov.domain.usecase.GetFilmByIdUseCase
import com.soldatov.domain.usecase.GetSimilarFilmsUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FilmFragmentViewModel(
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase,
    private val getFilmByIdUseCase: GetFilmByIdUseCase
) : ViewModel() {

    private val filmId = MutableLiveData<Long>()
    private val filmMode = MutableLiveData("")

    val filmById = Transformations.switchMap(filmId) { id ->
        liveData(Dispatchers.IO) {
            try {
                emit(FilmResult.Success(data = getFilmByIdUseCase.execute(filmId = id, filmMode.value!!)))
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

    fun setFilmMode(filmModeNew: String){
        filmMode.value = filmModeNew
    }
}