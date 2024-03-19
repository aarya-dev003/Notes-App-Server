package com.example

import com.example.auth.hash
import com.example.plugins.*
import com.example.repository.DatabaseFactory
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    DatabaseFactory.init()
    val hashFunction = { s: String ->
        hash(s)}
    configureSerialization()
    configureSecurity()
    configureRouting(hashFunction = { s: String -> hash(s) })
}
