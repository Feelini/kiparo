package com.soldatov.jokeabout.presentation.utils

import android.content.Context
import com.soldatov.jokeabout.data.repository.JokeRepositoryImpl
import com.soldatov.jokeabout.data.service.JokeAlarmService
import com.soldatov.jokeabout.data.storage.SharedPrefJokeStorage
import com.soldatov.jokeabout.domain.usecase.SaveDataUseCase
import com.soldatov.jokeabout.domain.usecase.StartAlarmManagerUseCase

object UseCaseProvider {

    private fun getRepository(context: Context): JokeRepositoryImpl{
        val sharedPrefJokeStorage = SharedPrefJokeStorage(context)
        val jokeAlarmService = JokeAlarmService(context)
        return JokeRepositoryImpl(sharedPrefJokeStorage, jokeAlarmService)
    }

    fun provideSaveDataUseCase(context: Context): SaveDataUseCase{
        return SaveDataUseCase(getRepository(context))
    }

    fun provideStartAlarmManagerUseCase(context: Context): StartAlarmManagerUseCase{
        return StartAlarmManagerUseCase(getRepository(context))
    }
}