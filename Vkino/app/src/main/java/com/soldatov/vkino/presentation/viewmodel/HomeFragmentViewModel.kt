package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.*
import com.soldatov.data.api.request_status.FilmsSliderResult
import com.soldatov.domain.usecase.GetHomeFilmsUseCase
import com.soldatov.domain.usecase.GetTopSliderUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeFragmentViewModel(
    private val getTopSliderUseCase: GetTopSliderUseCase,
    private val getHomeFilmsUseCase: GetHomeFilmsUseCase
) : ViewModel() {

    val topSliderInfo = liveData(Dispatchers.IO) {
        emit(FilmsSliderResult.Loading)
        try {
            emit(FilmsSliderResult.Success(data = getTopSliderUseCase.execute()))
        } catch (exception: Exception) {
            emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    val homePageFilms = liveData(Dispatchers.IO) {
        emit(FilmsSliderResult.Loading)
        try {
            emit(FilmsSliderResult.Success(data = getHomeFilmsUseCase.execute()))
        } catch (exception: Exception) {
            emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }
}