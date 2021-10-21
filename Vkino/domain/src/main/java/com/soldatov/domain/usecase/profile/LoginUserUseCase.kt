package com.soldatov.domain.usecase.profile

import com.soldatov.domain.models.profile.LoginData
import com.soldatov.domain.repository.FilmsRepository

class LoginUserUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(loginData: LoginData): String?{
        return filmsRepository.loginUser(loginData)
    }
}