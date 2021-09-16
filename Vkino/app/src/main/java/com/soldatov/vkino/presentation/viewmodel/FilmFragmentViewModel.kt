package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.*
import com.soldatov.data.api.FilmsSliderResult
import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.domain.usecase.GetFilmByIdUseCase
import com.soldatov.domain.usecase.GetSimilarFilmsUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FilmFragmentViewModel(
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase,
    private val getFilmByIdUseCase: GetFilmByIdUseCase
) : ViewModel() {

    fun getFilmById(filmId: Long): LiveData<DomainFilmSliderInfo> {
        return liveData {
            emit(getFilmByIdUseCase.execute(filmId))
        }
    }

    fun getSimilarFilms(filmId: Long): LiveData<FilmsSliderResult> {
        return liveData(Dispatchers.IO) {
            try {
                emit(FilmsSliderResult.Success(data = getSimilarFilmsUseCase.execute(filmId = filmId)))
            } catch (exception: Exception) {
                emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }
}