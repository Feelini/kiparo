package com.soldatov.jokeabout.data.service.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.*
import com.soldatov.jokeabout.data.service.workmanager.JokeWorker

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequestBuilder<JokeWorker>()
            .setConstraints(constraints)
            .build()
        if (p0 != null) {
            WorkManager.getInstance(p0).enqueue(request)
        }
    }
}