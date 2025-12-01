package com.example.videogamesapi.models

import java.io.Serializable

data class Games(
    val id: String,
    val title: String,
    val developer: String,
    val description: String,
    val image: String,
    val rating: Double,
    val gender: String
) : Serializable
