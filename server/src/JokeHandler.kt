package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

private val jokes = listOf(
    "is the only man who has, literally, beaten the odds. With his fists.",
    "keeps his friends close and his enemies closer. Close enough to drop them with one round house kick to the face.",
    "can make fire using two ice cubes."
)

fun Route.getJoke() {
    get("/joke") {
        val firstName = call.parameters["firstName"] ?: return@get call.respondText("Bad Request")
        val lastName = call.parameters["lastName"] ?: return@get call.respondText("Bad Request")
        val joke = jokes.random()
        val respond = "{ \"type\": \"success\", \"value\": {\"joke\": \"$firstName $lastName $joke\" } }"
        call.respond(respond)
    }
}