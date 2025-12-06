package com.example.videogamesapi.utils

import com.example.videogamesapi.models.Games

fun Games.safeImageUrl(): String {
    return if (!this.image.isNullOrBlank()) this.image
    else "https://via.placeholder.com/500x300.png?text=No+Image"
}

fun Games.safeCategories(): String {
    return ""
}

fun safeTitle(game: Games): String {
    return game.title.ifBlank { "Unknown" }
}
