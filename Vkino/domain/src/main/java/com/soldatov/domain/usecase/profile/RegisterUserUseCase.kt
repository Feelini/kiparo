package com.soldatov.domain.usecase.profile

import com.soldatov.domain.models.profile.RegisterData
import com.soldatov.domain.models.profile.RegisterResult
import com.soldatov.domain.repository.FilmsRepository

class RegisterUserUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(registerData: RegisterData): RegisterResult{
        return filmsRepository.registerUser(registerData)
    }
}