package com.example.data.model

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val hashPassword : String,
    val email : String
):Principal
