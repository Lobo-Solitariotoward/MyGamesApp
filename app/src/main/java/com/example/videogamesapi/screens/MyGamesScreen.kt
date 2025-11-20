package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Whatshot
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
import androidx.compose.ui.tooling.preview.Preview

// Paleta (misma que Chat/Explore)
private val BgColor   = Color(0xFF0C0F27)
private val CardColor = Color(0xFF1E1B1B)
private val OnBg      = Color.White
private val Muted     = Color(0xFFB0B0B0)
private val Accent    = Color(0xFF7A6BFF)

// --- Datos mock ---
data class GameTile(
    val id: String,
    val title: String,
    val imageUrl: String
)

data class CaptureTile(
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

private val mockCaptures = listOf(
    CaptureTile("c1","Headshot Pro • 10/10", squareUrl("cap_headshot")),
    CaptureTile("c2","Boss vencido", squareUrl("cap_boss")),
    CaptureTile("c3","Construcción épica", squareUrl("cap_build")),
    CaptureTile("c4","Victoria Royale", squareUrl("cap_victory")),
    CaptureTile("c5","Foto del clan", squareUrl("cap_clan")),
    CaptureTile("c6","Paisaje del mapa", squareUrl("cap_landscape")),
    CaptureTile("c7","Auto legendario", squareUrl("cap_car")),
    CaptureTile("c8","Loot SSS", squareUrl("cap_loot")),
    CaptureTile("c9","Combo x120", squareUrl("cap_combo"))
)

// --- UI ---
@Composable
fun MyGamesScreen() {
    var selectedTab by remember { mutableStateOf(1) } // 0: Capturas, 1: Juegos, 2: Consolas
    val tabs = listOf("Capturas", "Juegos", "Consolas")

    Scaffold(
        containerColor = BgColor,
        topBar = {
            // Header grande tipo “Mi biblioteca”
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgColor)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Mi biblioteca",
                    color = OnBg,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(10.dp))

                // Tabs
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    tabs.forEachIndexed { index, label ->
                        Column(
                            modifier = Modifier
                                .padding(end = 18.dp)
                                .clickable { selectedTab = index },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = label,
                                color = if (selectedTab == index) OnBg else Muted,
                                fontSize = 16.sp,
                                fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal
                            )
                            Spacer(Modifier.height(4.dp))
                            if (selectedTab == index) {
                                Box(
                                    Modifier
                                        .height(3.dp)
                                        .width(56.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(OnBg)
                                )
                            } else {
                                Spacer(Modifier.height(3.dp))
                            }
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    // Botones ordenar / filtrar
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        SmallRoundIcon(Icons.Filled.FilterList, contentDesc = "Filtrar")
                        SmallRoundIcon(Icons.Filled.Sort, contentDesc = "Ordenar")
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Contador (cambia según pestaña)
                val countText = when (selectedTab) {
                    0 -> "${mockCaptures.size} capturas"
                    1 -> "${mockGames.size} juegos"
                    else -> "0 consolas"
                }
                Text(
                    text = countText,
                    color = OnBg,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    ) { padding ->

        when (selectedTab) {
            // --- Capturas ---
            0 -> LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(BgColor)
                    .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mockCaptures) { cap ->
                    CaptureSquare(cap)
                }
            }

            // --- Juegos ---
            1 -> LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(BgColor)
                    .padding(horizontal = 10.dp),
                contentPadding = PaddingValues(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mockGames) { game ->
                    GameSquare(game)
                }
            }

            // --- Consolas (placeholder) ---
            else -> Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(BgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Sin consolas registradas", color = Muted)
            }
        }
    }
}

@Composable
private fun SmallRoundIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, contentDesc: String) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(CardColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = contentDesc, tint = OnBg)
    }
}

@Composable
private fun GameSquare(game: GameTile) {
    Column {
        val shape = RoundedCornerShape(14.dp)
        Box(
            modifier = Modifier
                .aspectRatio(1f) // cuadrado
                .clip(shape)
                .background(CardColor)
        ) {
            AsyncImage(
                model = game.imageUrl,
                contentDescription = game.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Indicador (dispositivo)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xAA000000))
                    .padding(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Smartphone,
                    contentDescription = "Disponible",
                    tint = OnBg
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = game.title,
            color = OnBg,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CaptureSquare(capture: CaptureTile) {
    Column {
        val shape = RoundedCornerShape(14.dp)
        Box(
            modifier = Modifier
                .aspectRatio(1f) // cuadrado
                .clip(shape)
                .background(CardColor)
        ) {
            AsyncImage(
                model = capture.imageUrl,
                contentDescription = capture.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = capture.title,
            color = Muted,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyGamesScreenPreview() {
    MyGamesScreen()
}
