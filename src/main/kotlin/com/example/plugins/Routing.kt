package com.example.plugins

import com.example.auth.JwtService
import com.example.repository.Repo
import com.example.routes.NoteRoute
import com.example.routes.signIn
import com.example.routes.signUp
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Application.configureRouting(
    hashFunction: (String) -> String){
    val db = Repo()
    val jwtService = JwtService()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            signUp(db, jwtService, hashFunction)
            signIn(db,jwtService,hashFunction)
            NoteRoute(db,hashFunction)
        }
    }
}
