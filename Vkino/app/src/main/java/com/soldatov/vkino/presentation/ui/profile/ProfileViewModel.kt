package com.soldatov.vkino.presentation.ui.profile

import androidx.lifecycle.*
import com.soldatov.domain.models.profile.*
import com.soldatov.domain.usecase.profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveUserTokenUseCase: SetUserTokenUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val quitProfileUseCase: QuitProfileUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val userToken = MutableLiveData("")
    private val isLogIn = MutableLiveData(false).apply {
        val token = getUserToken()
        if (token != ""){
            userToken.value = token
            this.value = true
        }
    }

    val userInfo = Transformations.switchMap(userToken){ token ->
        liveData(Dispatchers.IO){
            emit(getUserInfoUseCase.execute(token))
        }
    } as MutableLiveData

    fun updateUserInfo(userProfileInfo: UserInfo){
        viewModelScope.launch {
            userInfo.postValue(updateProfileUseCase.execute(userProfileInfo, getUserToken()))
        }
    }

    fun sendLoginRequest(loginData: LoginData): LiveData<String?> {
        return liveData(Dispatchers.IO) {
            emit(loginUserUseCase.execute(loginData))
        }
    }

    fun setUserToken(token: String) {
        saveUserTokenUseCase.execute(token)
        userToken.value = token
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
        userToken.value = ""
        isLogIn.value = false
    }

    fun sendRegisterRequest(registerData: RegisterData): LiveData<RegisterResult>{
        return liveData(Dispatchers.IO){
            emit(registerUserUseCase.execute(registerData))
        }
    }
}