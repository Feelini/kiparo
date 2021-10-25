package com.soldatov.domain.usecase.profile

import com.soldatov.domain.models.profile.UserInfoResult
import com.soldatov.domain.repository.FilmsRepository

class GetUserInfoUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(token: String): UserInfoResult{
        return filmsRepository.getUserInfo(token)
    }
}