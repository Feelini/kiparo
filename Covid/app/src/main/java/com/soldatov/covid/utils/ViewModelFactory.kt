package com.soldatov.covid.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soldatov.covid.data.api.ApiHelper
import com.soldatov.covid.data.repository.CovidRepositoryImpl
import com.soldatov.covid.presentation.MainActivityViewModel


class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(CovidRepositoryImpl(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}