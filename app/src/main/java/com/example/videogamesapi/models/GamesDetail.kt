package com.example.videogamesapi.models


data class GamesDetail (
    val id: Int,
    val title: String,
    val category: String,
    val headerImageUrl: String,
    val avatarImageUrl: String,
    val description: String,
    val rating: String,
    val playTime: String,
    val playerCount: String,
    val screenshots: List<String>, // Galería de imágenes del juego (reemplaza streams)
    val playersOnline: List<PlayerOnline>
)

data class PlayerOnline(
    val username: String,
    val avatarUrl: String
)