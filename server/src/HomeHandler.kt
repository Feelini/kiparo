package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

private const val HELLO_MESSAGE = "Hello World!"

fun Route.getHome() {
    get("/"){
        call.respondText(HELLO_MESSAGE)
    }
}