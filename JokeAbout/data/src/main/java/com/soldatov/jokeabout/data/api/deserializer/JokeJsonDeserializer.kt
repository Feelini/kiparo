package com.soldatov.jokeabout.data.api.deserializer

import com.soldatov.jokeabout.data.api.models.Joke
import com.soldatov.jokeabout.data.api.models.JokeValue
import org.json.JSONObject

class JokeJsonDeserializer {

    fun getJoke(data: String?): Joke? {
        if (data == null || data.trim().isEmpty()) return null

        val jsonObject = JSONObject(data)
        return Joke(
            jsonObject.getString("type"),
            getJokeValue(jsonObject.getJSONObject("value"))
        )
    }

    private fun getJokeValue(jokeValueJson: JSONObject): JokeValue {
        val categories = ArrayList<String>()
        val jsonCategories = jokeValueJson.getJSONArray("categories")
        if (jsonCategories.length() > 0) {
            for (i in 0 until jsonCategories.length()) {
                categories.add(jsonCategories.getString(i))
            }
        }
        return JokeValue(
            jokeValueJson.getInt("id"),
            jokeValueJson.getString("joke"),
            categories
        )
    }
}