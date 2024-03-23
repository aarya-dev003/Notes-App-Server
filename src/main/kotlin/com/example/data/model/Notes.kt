package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Notes(
    val id : String,
    val noteTitle: String,
    val description: String,
    val date : Long
)
