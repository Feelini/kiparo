package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.data.api.Result
import com.soldatov.domain.usecase.GetTopSliderUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainActivityViewModel(private val getTopSliderUseCase: GetTopSliderUseCase): ViewModel() {

    val topSliderInfo = liveData(Dispatchers.IO){
        emit(Result.Loading)
        try {
            emit(Result.Success(data = getTopSliderUseCase.execute()))
        } catch (exception: Exception){
            emit(Result.Error(message = exception.message ?: "Error Occurred"))
        }
    }
}