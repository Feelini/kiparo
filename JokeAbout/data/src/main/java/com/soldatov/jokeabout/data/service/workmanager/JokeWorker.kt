package com.soldatov.jokeabout.data.service.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.soldatov.jokeabout.data.api.JokeApiImpl
import com.soldatov.jokeabout.data.api.models.JokeRequestData
import com.soldatov.jokeabout.data.repository.JokeRepositoryImpl
import com.soldatov.jokeabout.data.service.alarmmanager.JokeAlarmService
import com.soldatov.jokeabout.data.storage.SharedPrefJokeStorage
import com.soldatov.jokeabout.data.utils.JokeNotification

const val CHANNEL_ID = "uniqueId"
const val NOTIFICATION_ID = 101
const val NOTIFICATION_TITLE = "New joke"
const val ACTIVITY_TO_START = "com.soldatov.jokeabout.presentation.JokeAboutActivity"

class JokeWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        val jokeStorage = SharedPrefJokeStorage(applicationContext)
        val jokeService = JokeAlarmService(applicationContext)
        val jokeRepositoryImpl = JokeRepositoryImpl(jokeStorage, jokeService)
        val data = jokeRepositoryImpl.getData()
        val jokeRequestData = JokeRequestData(data.firstName, data.lastName)
        val jokeApiImpl = JokeApiImpl()
        val joke = jokeApiImpl.getJoke(applicationContext, jokeRequestData)
        val jokeNotification = JokeNotification()
        if (joke != null) {
            jokeNotification.showNotification(applicationContext, joke.value.joke)
        }
        return Result.success()
    }

}