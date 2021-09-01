package com.soldatov.jokeabout.data.api.models

data class JokeValue(
    val id: Int,
    val joke: String,
    val categories: List<String>
)
