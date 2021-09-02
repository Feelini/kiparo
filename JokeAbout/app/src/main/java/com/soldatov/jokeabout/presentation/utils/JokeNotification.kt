package com.soldatov.jokeabout.presentation.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.soldatov.jokeabout.R
import com.soldatov.jokeabout.data.api.CHANNEL_ID
import com.soldatov.jokeabout.data.api.NOTIFICATION_ID
import com.soldatov.jokeabout.presentation.JokeAboutActivity

class JokeNotification {

    fun showNotification(context: Context, contentText: String){
        val intent = Intent(context, JokeAboutActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.sentiment_very_satisfied)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(contentText)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)){
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}