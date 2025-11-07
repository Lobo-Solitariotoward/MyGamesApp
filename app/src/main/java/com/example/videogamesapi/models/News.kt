package com.example.videogamesapi.models

data class News (
    val id : Int,
    val title : String,
    val source : String,
    val timeAgo : String,
    val imageUrl : String,
)