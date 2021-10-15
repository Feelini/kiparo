package com.soldatov.vkino.presentation.ui.home

import androidx.lifecycle.*
import com.soldatov.data.api.request_status.FilmsResult
import com.soldatov.data.api.request_status.FilmsSliderResult
import com.soldatov.domain.usecase.GetHomeFilmsUseCase
import com.soldatov.domain.usecase.GetTopSliderUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeFragmentViewModel(
    private val getTopSliderUseCase: GetTopSliderUseCase,
    private val getHomeFilmsUseCase: GetHomeFilmsUseCase
) : ViewModel() {

    private var page = MutableLiveData(1)

    val topSliderInfo = liveData(Dispatchers.IO) {
        emit(FilmsSliderResult.Loading)
        try {
            emit(FilmsSliderResult.Success(data = getTopSliderUseCase.execute()))
        } catch (exception: Exception) {
            emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    val homePageFilms = Transformations.switchMap(page) { page ->
        liveData(Dispatchers.IO) {
            emit(FilmsSliderResult.Loading)
            try {
                emit(FilmsResult.Success(data = getHomeFilmsUseCase.execute(page)))
            } catch (exception: Exception) {
                emit(FilmsResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    fun nextPage(){
        page.value = page.value?.plus(1)
    }
}