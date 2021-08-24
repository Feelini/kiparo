package com.soldatov.covid.presentation.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soldatov.covid.data.repository.CovidRepositoryImpl
import com.soldatov.covid.presentation.MainActivityViewModel


class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(application, CovidRepositoryImpl()) as T
        }
        return MainActivityViewModel(application, CovidRepositoryImpl()) as T
    }
}