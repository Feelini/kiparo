package com.soldatov.vkino.presentation.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.data.api.request_status.GenresResult
import com.soldatov.data.api.request_status.YearsResult
import com.soldatov.domain.models.Years
import com.soldatov.domain.usecase.filter.GetGenresUseCase
import com.soldatov.domain.usecase.filter.GetYearsUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FilterFragmentViewModel(
    private val getGenresUseCase: GetGenresUseCase,
    private val getYearsUseCase: GetYearsUseCase
): ViewModel() {

    val genres = liveData(Dispatchers.IO) {
        emit(GenresResult.Loading)
        try {
            emit(GenresResult.Success(data = getGenresUseCase.execute()))
        } catch (exception: Exception) {
            emit(GenresResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    val years = liveData(Dispatchers.IO) {
        emit(YearsResult.Loading)
        try {
            emit(YearsResult.Success(data = getYearsUseCase.execute()))
        } catch (exception: Exception){
            emit(YearsResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    fun getYears(): Years{
        return (years.value as YearsResult.Success).data
    }
}