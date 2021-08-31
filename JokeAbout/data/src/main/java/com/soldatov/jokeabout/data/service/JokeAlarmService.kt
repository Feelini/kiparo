package com.soldatov.jokeabout.data.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.util.*

class JokeAlarmService(private val context: Context) : JokeService {

    override fun startAlarmManager(repeatingTime: Long) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val notifyIntent = Intent(context, AlarmReceiver::class.java)
        val notifySender = PendingIntent.getBroadcast(context, 0, notifyIntent, 0)
        val calendar = Calendar.getInstance()
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            repeatingTime,
            notifySender
        )
    }
}