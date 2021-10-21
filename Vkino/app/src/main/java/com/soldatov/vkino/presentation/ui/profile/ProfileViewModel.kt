package com.soldatov.vkino.presentation.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.soldatov.domain.models.profile.LoginData
import com.soldatov.domain.usecase.profile.LoginUserUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProfileViewModel(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {

    private val loginUserData = MutableLiveData<LoginData>()

    val loginRequest = Transformations.switchMap(loginUserData) {loginData ->
        liveData(Dispatchers.IO){
            try {
                emit(loginUserUseCase.execute(loginData))
            } catch (exception: Exception) {
                emit(exception.message ?: "Error Occurred")
            }
        }
    }

    fun setLoginData(loginData: LoginData){
        loginUserData.value = loginData
    }
}