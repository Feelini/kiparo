package com.soldatov.covid.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.soldatov.covid.domain.repository.CovidRepository
import androidx.lifecycle.MutableLiveData
import com.soldatov.covid.domain.models.CovidInfo


class MainActivityViewModel(
    application: Application,
    private val repository: CovidRepository
) : AndroidViewModel(application) {

    private val covidInfoLiveData: MutableLiveData<CovidInfo>? = null

    fun fetchCovidInfo() {
        repository.getCovidInfo().thenAccept {
            covidInfoLiveData?.postValue(it)
        }
    }

    fun getCovidInfo(): LiveData<CovidInfo>? {
        return covidInfoLiveData
    }
}