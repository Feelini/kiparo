package com.soldatov.taxi.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soldatov.taxi.presentation.viewmodel.MainActivityViewModel

class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(UseCaseProvider.provideGetTaxiInfoUseCase()) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}