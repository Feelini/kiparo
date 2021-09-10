package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soldatov.domain.models.DomainTopSliderInfo
import com.soldatov.domain.usecase.GetTopSliderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val getTopSliderUseCase: GetTopSliderUseCase): ViewModel() {
    private val topSliderInfo: MutableLiveData<DomainTopSliderInfo> by lazy {
        MutableLiveData<DomainTopSliderInfo>().also {
            getTopSliderInfoFromRepo()
        }
    }

    fun getTopSliderInfo(): LiveData<DomainTopSliderInfo>{
        return topSliderInfo
    }

    private fun getTopSliderInfoFromRepo() {
        viewModelScope.launch(Dispatchers.IO){
            getTopSliderUseCase.execute()
        }
    }
}