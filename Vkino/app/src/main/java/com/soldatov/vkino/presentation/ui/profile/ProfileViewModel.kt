package com.soldatov.vkino.presentation.ui.profile

import androidx.lifecycle.*
import com.soldatov.domain.models.profile.LoginData
import com.soldatov.domain.usecase.profile.LoginUserUseCase
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {

    fun sendLoginRequest(loginData: LoginData): LiveData<String?>{
        return liveData(Dispatchers.IO) {
            emit(loginUserUseCase.execute(loginData))
        }
    }
}