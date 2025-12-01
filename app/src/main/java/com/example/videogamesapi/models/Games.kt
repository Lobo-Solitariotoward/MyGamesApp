package com.example.videogamesapi.models

data class Games(
    val id: String,
    val title: String,
    val developer: String,
    val description: String,
    val image: String,
    val rating: Double,
    val gender: String
)
