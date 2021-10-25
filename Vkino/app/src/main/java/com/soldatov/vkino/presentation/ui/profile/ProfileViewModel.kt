package com.soldatov.vkino.presentation.ui.profile

import androidx.lifecycle.*
import com.soldatov.domain.models.profile.LoginData
import com.soldatov.domain.models.profile.RegisterData
import com.soldatov.domain.models.profile.RegisterResult
import com.soldatov.domain.models.profile.UserInfo
import com.soldatov.domain.usecase.profile.*
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveUserTokenUseCase: SetUserTokenUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val quitProfileUseCase: QuitProfileUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val isLogIn = MutableLiveData(false).apply {
        val token = getUserToken()
        if (token != ""){
            this.value = true
        }
    }

    val userInfo = liveData {
        emit(getUserInfoUseCase.execute())
    }

    fun sendLoginRequest(loginData: LoginData): LiveData<String?> {
        return liveData(Dispatchers.IO) {
            emit(loginUserUseCase.execute(loginData))
        }
    }

    fun setUserToken(token: String) {
        saveUserTokenUseCase.execute(token)
        isLogIn.value = true
    }

    fun getUserToken(): String{
        return getUserTokenUseCase.execute()
    }

    fun isLoggedIn(): LiveData<Boolean>{
        return isLogIn
    }

    fun quitProfile(){
        quitProfileUseCase.execute()
        isLogIn.value = false
    }

    fun sendRegisterRequest(registerData: RegisterData): LiveData<RegisterResult>{
        return liveData(Dispatchers.IO){
            emit(registerUserUseCase.execute(registerData))
        }
    }
}