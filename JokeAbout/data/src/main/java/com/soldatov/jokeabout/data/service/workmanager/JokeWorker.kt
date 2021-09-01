package com.soldatov.jokeabout.data.service.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.soldatov.jokeabout.data.api.JokeApiImpl
import com.soldatov.jokeabout.data.api.models.JokeRequestData
import com.soldatov.jokeabout.data.repository.JokeRepositoryImpl
import com.soldatov.jokeabout.data.service.alarmmanager.JokeAlarmService
import com.soldatov.jokeabout.data.storage.SharedPrefJokeStorage

class JokeWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d("TAG", "do work")
        val jokeStorage = SharedPrefJokeStorage(applicationContext)
        val jokeService = JokeAlarmService(applicationContext)
        val jokeRepositoryImpl = JokeRepositoryImpl(jokeStorage, jokeService)
        val data = jokeRepositoryImpl.getData()
        val jokeRequestData = JokeRequestData(data.firstName, data.lastName)
        val jokeApiImpl = JokeApiImpl()
        jokeApiImpl.getJoke(jokeRequestData)
        return Result.success()
    }

}