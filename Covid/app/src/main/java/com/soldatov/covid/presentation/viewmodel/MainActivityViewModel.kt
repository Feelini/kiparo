package com.soldatov.covid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.covid.domain.models.DomainCovidInfo
import com.soldatov.covid.domain.usecase.GetCovidInfoUseCase
import com.soldatov.covid.utils.Result
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainActivityViewModel(private val getCovidInfoUseCase: GetCovidInfoUseCase) : ViewModel() {

    fun getCovidInfo(): DomainCovidInfo? {
        return when (covidInfo.value) {
            is Result.Success -> {
                return (covidInfo.value as Result.Success).data
            }
            else -> null
        }
    }

    val covidInfo = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            emit(Result.Success(data = getCovidInfoUseCase.execute()))
        } catch (exception: Exception) {
            emit(Result.Error(message = exception.message ?: "Error Occurred!"))
        }
    }
}