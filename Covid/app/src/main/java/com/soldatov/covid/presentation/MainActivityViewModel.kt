package com.soldatov.covid.presentation

import com.soldatov.covid.domain.repository.CovidRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.covid.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception


class MainActivityViewModel(private val covidRepository: CovidRepository) : ViewModel() {

    fun getCovidInfo() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = covidRepository.getCovidInfo()))
        } catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}