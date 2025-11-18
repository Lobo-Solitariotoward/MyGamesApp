package com.example.videogamesapi.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview

//Colores
private val BgColor   = Color(0xFF0C0F27)
private val CardColor = Color(0xFF1E1B1B)
private val OnBg      = Color.White
private val Muted     = Color(0xFFB0B0B0)
private val Accent    = Color(0xFF7A6BFF)

// Datos de prueba
data class GameTile(
    val id: String,
    val title: String,
    val imageUrl: String
)

private fun squareUrl(seed: String) = "https://picsum.photos/seed/$seed/600/600"

private val mockGames = listOf(
    GameTile("1","Call of Duty: La Maldición", squareUrl("cod_maldicion")),
    GameTile("2","Candy Saga", squareUrl("candysaga")),
    GameTile("3","Fallout", squareUrl("fallout1")),
    GameTile("4","Fallout 2", squareUrl("fallout2")),
    GameTile("5","Fallout 3", squareUrl("fallout3")),
    GameTile("6","Fallout New Vegas", squareUrl("newvegas")),
    GameTile("7","Fortnite", squareUrl("fortnite")),
    GameTile("8","Killer Instinct", squareUrl("ki")),
    GameTile("9","Minecraft", squareUrl("minecraft1")),
    GameTile("10","Minecraft Preview", squareUrl("minecraft2")),
    GameTile("11","Retro Arcade", squareUrl("retro")),
    GameTile("12","Ori and the Forest", squareUrl("ori")),
    GameTile("13","Sea of Thieves", squareUrl("sea")),
    GameTile("14","Forza Horizon", squareUrl("forza")),
    GameTile("15","Halo Infinite", squareUrl("halo")),
    GameTile("16","Gears 5", squareUrl("gears")),
    GameTile("17","Hi-Fi Rush", squareUrl("hifi"))
)

