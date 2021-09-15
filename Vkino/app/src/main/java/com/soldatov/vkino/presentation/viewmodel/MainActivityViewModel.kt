package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.*
import com.soldatov.data.api.FilmsSliderResult
import com.soldatov.domain.models.DomainFilmSliderInfo
import com.soldatov.domain.usecase.GetSimilarFilmsUseCase
import com.soldatov.domain.usecase.GetTopSliderUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainActivityViewModel(
    private val getTopSliderUseCase: GetTopSliderUseCase,
    private val getSimilarFilmsUseCase: GetSimilarFilmsUseCase
) : ViewModel() {

    val topSliderInfo = liveData(Dispatchers.IO) {
        emit(FilmsSliderResult.Loading)
        try {
            emit(FilmsSliderResult.Success(data = getTopSliderUseCase.execute()))
        } catch (exception: Exception) {
            emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    fun getTopSliderFilmById(filmId: Long): DomainFilmSliderInfo? {
        return when (topSliderInfo.value) {
            is FilmsSliderResult.Success -> {
                (topSliderInfo.value as FilmsSliderResult.Success).data.filter { it.filmId == filmId }[0]
            }
            else -> null
        }
    }

    private val filmIdInput = MutableLiveData<Long>()
    val similarFilms: LiveData<FilmsSliderResult> =
        Transformations.switchMap(filmIdInput) {
            liveData(Dispatchers.IO) {
                try {
                    emit(FilmsSliderResult.Success(data = getSimilarFilmsUseCase.execute(filmId = it)))
                } catch (exception: Exception) {
                    emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
                }
            }
        }

    fun setFilmIdInput(filmId: Long) {
        filmIdInput.value = filmId
    }
}