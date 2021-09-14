package com.soldatov.vkino.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.data.api.TopSliderResult
import com.soldatov.domain.models.DomainTopSliderInfo
import com.soldatov.domain.usecase.GetTopSliderUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainActivityViewModel(private val getTopSliderUseCase: GetTopSliderUseCase) : ViewModel() {

    val topSliderInfo = liveData(Dispatchers.IO) {
        emit(TopSliderResult.Loading)
        try {
            emit(TopSliderResult.Success(data = getTopSliderUseCase.execute()))
        } catch (exception: Exception) {
            emit(TopSliderResult.Error(message = exception.message ?: "Error Occurred"))
        }
    }

    fun getFilmById(filmId: Long): DomainTopSliderInfo? {
        return when (topSliderInfo.value) {
            is TopSliderResult.Success -> {
                (topSliderInfo.value as TopSliderResult.Success).data.filter { it.filmId == filmId }[0]
            }
            else -> null
        }
    }
}