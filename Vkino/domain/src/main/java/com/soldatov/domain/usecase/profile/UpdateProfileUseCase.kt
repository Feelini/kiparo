package com.soldatov.domain.usecase.profile

import com.soldatov.domain.models.profile.UserInfo
import com.soldatov.domain.models.profile.UserInfoResult
import com.soldatov.domain.repository.FilmsRepository

class UpdateProfileUseCase(private val filmsRepository: FilmsRepository) {

    suspend fun execute(userInfo: UserInfo): UserInfoResult{
        return filmsRepository.updateProfile(userInfo)
    }
}