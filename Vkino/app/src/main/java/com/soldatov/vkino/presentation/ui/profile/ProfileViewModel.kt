package com.soldatov.vkino.presentation.ui.profile

import androidx.lifecycle.*
import com.soldatov.domain.models.profile.*
import com.soldatov.domain.usecase.profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val saveUserTokenUseCase: SetUserTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val quitProfileUseCase: QuitProfileUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val isLogInUseCase: IsLogInUseCase
) : ViewModel() {

    private val isLogIn = MutableLiveData(false).apply {
        this.value = isLogInUseCase.execute()
    }

    val userInfo = Transformations.switchMap(isLogIn){
        liveData(Dispatchers.IO){
            emit(getUserInfoUseCase.execute())
        }
    } as MutableLiveData

    fun updateUserInfo(userProfileInfo: UserInfo){
        viewModelScope.launch {
            userInfo.postValue(updateProfileUseCase.execute(userProfileInfo))
        }
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