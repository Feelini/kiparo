package com.soldatov.jokeabout.data.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.soldatov.jokeabout.data.R
import com.soldatov.jokeabout.data.service.workmanager.ACTIVITY_TO_START
import com.soldatov.jokeabout.data.service.workmanager.CHANNEL_ID
import com.soldatov.jokeabout.data.service.workmanager.NOTIFICATION_ID
import com.soldatov.jokeabout.data.service.workmanager.NOTIFICATION_TITLE

class JokeNotification {

    fun showNotification(context: Context, contentText: String){
        var intent: Intent? = null
        try {
            val c = Class.forName(ACTIVITY_TO_START)
            intent = Intent(context, c)
            intent.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        } catch (ignored: ClassNotFoundException){
        }
        if (intent != null) {
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.sentiment_very_satisfied)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(contentText))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }
}