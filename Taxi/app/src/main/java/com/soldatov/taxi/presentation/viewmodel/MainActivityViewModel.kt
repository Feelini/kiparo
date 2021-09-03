package com.soldatov.taxi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.data.utils.Result
import com.soldatov.domain.usecase.GetTaxiInfoUseCase
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val getTaxiInfoUseCase: GetTaxiInfoUseCase): ViewModel() {

    val taxiInfo = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            emit(Result.Success(data = getTaxiInfoUseCase.execute()))
        } catch (exception: Exception){
            emit(Result.Error(message = exception.message ?: "Error Occurred"))
        }
    }
}