package com.soldatov.taxi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.data.utils.Result
import com.soldatov.domain.models.DomainTaxiInfo
import com.soldatov.domain.usecase.GetTaxiInfoUseCase
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val getTaxiInfoUseCase: GetTaxiInfoUseCase): ViewModel() {

    fun getTaxiInfo(): List<DomainTaxiInfo>?{
        return when (taxiInfo.value){
            is Result.Success -> (taxiInfo.value as Result.Success).data
            else -> null
        }
    }

    val taxiInfo = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            emit(Result.Success(data = getTaxiInfoUseCase.execute()))
        } catch (exception: Exception){
            emit(Result.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    private val selectedTaxi: MutableLiveData<DomainTaxiInfo> = MutableLiveData()

    fun getSelectedTaxi(): LiveData<DomainTaxiInfo>{
        return selectedTaxi
    }

    fun setSelectedTaxi(taxiInfo: DomainTaxiInfo){
        selectedTaxi.value = taxiInfo
    }
}