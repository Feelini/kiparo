package com.soldatov.vkino.presentation.ui.home

import androidx.lifecycle.*
import com.soldatov.domain.models.FilterParams
import com.soldatov.domain.models.result.FilmsResult
import com.soldatov.domain.models.result.FilmsSliderResult
import com.soldatov.domain.usecase.GetHomeFilmsUseCase
import com.soldatov.domain.usecase.GetTopSliderUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeFragmentViewModel(
    private val getTopSliderUseCase: GetTopSliderUseCase,
    private val getHomeFilmsUseCase: GetHomeFilmsUseCase
) : ViewModel() {

    private var filterParams = MutableLiveData(FilterParams())

    val topSliderInfo = liveData(Dispatchers.IO) {
        emit(FilmsSliderResult.Loading)
        try {
            emit(FilmsSliderResult.Success(data = getTopSliderUseCase.execute()))
        } catch (exception: Exception) {
            emit(FilmsSliderResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    val homePageFilms = Transformations.switchMap(filterParams) { filterParams ->
        liveData(Dispatchers.IO) {
            emit(FilmsSliderResult.Loading)
            try {
                emit(FilmsResult.Success(data = getHomeFilmsUseCase.execute(filterParams)))
            } catch (exception: Exception) {
                emit(FilmsResult.Error(message = exception.message ?: "Error Occurred"))
            }
        }
    }

    fun nextPage(){
        val currentParams = filterParams.value
        currentParams?.page = currentParams?.page?.plus(1) ?: 1
        filterParams.postValue(currentParams)
    }

    fun setFilterParams(newFilterParams: FilterParams){
        filterParams.value = newFilterParams
    }
}