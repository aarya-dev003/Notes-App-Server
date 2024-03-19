package com.example.routes

import com.example.auth.JwtService
import com.example.data.model.LoginRequest
import com.example.data.model.RegisterRequest
import com.example.data.model.SimpleResponse
import com.example.data.model.User
import com.example.repository.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

suspend fun Route.signUp(
    db: Repo,
    jwtService: JwtService,
    hashFunction: (String) -> String
) {

    post("register") {
        val registerRequest = call.receiveOrNull<RegisterRequest>()
        if (registerRequest == null) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Incorrect request format"))
            return@post
        }

        try {
            val user = User(
                email = registerRequest.email,
                hashPassword = hashFunction(registerRequest.password),
                name = registerRequest.name
            )
            db.addUser(user)
            call.respond(HttpStatusCode.OK, SimpleResponse(true, jwtService.generateToken(user)))
        } catch (e: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, e.message ?: "Invalid input"))
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                SimpleResponse(false, e.message ?: "Internal server error")
            )
        }
    }

}

suspend fun Route.signIn(
    db: Repo,
    jwtService: JwtService,
    hashFunction: (String) -> String
){

    post("login") {
        val loginRequest = call.receiveOrNull<LoginRequest>()
        if (loginRequest == null) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Incorrect request format"))
            return@post
        }

        try {
            val user = db.findUserByEmail(loginRequest.email)

            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Wrong email ID"))
            } else {
                if (user.hashPassword == hashFunction(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, jwtService.generateToken(user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Incorrect password"))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SimpleResponse(false, e.message ?: "Internal server error"))
        }
    }
}
