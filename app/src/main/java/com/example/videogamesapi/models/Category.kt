package com.example.videogamesapi.models

data class Category(
    val id: Int,
    val name: String,
    val isSelected : Boolean = false
)