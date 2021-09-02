package com.soldatov.jokeabout.data.service

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.soldatov.jokeabout.data.R
import com.soldatov.jokeabout.data.api.APP_NAME
import com.soldatov.jokeabout.data.api.CHANNEL_ID
import com.soldatov.jokeabout.data.api.NOTIFICATION_ID

class JokeNotification {

    fun createNotification(context: Context, contentText: String){
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.sentiment_very_satisfied)
            .setContentTitle(APP_NAME)
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)){
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}