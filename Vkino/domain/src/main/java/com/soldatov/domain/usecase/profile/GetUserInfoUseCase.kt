package com.soldatov.domain.usecase.profile

import com.soldatov.domain.models.profile.UserInfoResult
import com.soldatov.domain.repository.FilmsRepository

class GetUserInfoUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(): UserInfoResult{
        return filmsRepository.getUserInfo()
    }
}