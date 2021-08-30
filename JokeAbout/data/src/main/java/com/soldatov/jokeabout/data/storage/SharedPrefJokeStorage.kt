package com.soldatov.jokeabout.data.storage

import android.content.Context
import com.soldatov.jokeabout.data.storage.models.SharedPrefsData

private const val SHARED_PREFS_NAME = "joke_prefs"
private const val KEY_TIME = "time_in_sec"
private const val KEY_FIRST_NAME = "first_name"
private const val KEY_LAST_NAME = "last_name"

class SharedPrefJokeStorage(context: Context) : JokeStorage {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveData(data: SharedPrefsData): Boolean {
        sharedPreferences.edit().putInt(KEY_TIME, data.timeInSec).apply()
        sharedPreferences.edit().putString(KEY_FIRST_NAME, data.firstName).apply()
        sharedPreferences.edit().putString(KEY_LAST_NAME, data.lastName).apply()
        return true
    }

    override fun getData(): SharedPrefsData {
        val timeInSec = sharedPreferences.getInt(KEY_TIME, 0)
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, "") ?: ""

        return SharedPrefsData(timeInSec = timeInSec, firstName = firstName, lastName = lastName)
    }
}