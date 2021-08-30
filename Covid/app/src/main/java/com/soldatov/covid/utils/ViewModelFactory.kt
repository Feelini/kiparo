package com.soldatov.covid.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soldatov.covid.presentation.viewmodel.MainActivityViewModel
import com.soldatov.covid.presentation.utils.UseCaseProvider


class ViewModelFactory :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(UseCaseProvider.provideUserCase()) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}