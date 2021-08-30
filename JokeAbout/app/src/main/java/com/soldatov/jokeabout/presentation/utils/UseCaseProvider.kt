package com.soldatov.jokeabout.presentation.utils

import android.content.Context
import com.soldatov.jokeabout.data.repository.JokeRepositoryImpl
import com.soldatov.jokeabout.data.storage.SharedPrefJokeStorage
import com.soldatov.jokeabout.domain.usecase.SaveDataUseCase

object UseCaseProvider {

    fun provideSaveDataUseCase(context: Context): SaveDataUseCase{
        val sharedPrefJokeStorage = SharedPrefJokeStorage(context)
        val jokeRepositoryImpl = JokeRepositoryImpl(sharedPrefJokeStorage)
        return SaveDataUseCase(jokeRepositoryImpl)
    }
}