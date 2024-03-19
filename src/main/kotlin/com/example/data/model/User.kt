package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val hashPassword : String,
    val email : String
)
