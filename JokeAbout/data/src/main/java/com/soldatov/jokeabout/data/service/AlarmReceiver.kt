package com.soldatov.jokeabout.data.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Hooray", Toast.LENGTH_LONG).show()
    }
}