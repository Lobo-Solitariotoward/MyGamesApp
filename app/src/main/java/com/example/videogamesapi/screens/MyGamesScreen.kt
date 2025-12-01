package com.example.videogamesapi.screens

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
import androidx.compose.material.icons.filled.Smartphone
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.screens.viewmodel.HomeViewModel

// Paleta
private val BgColor   = Color(0xFF0C0F27)
private val CardColor = Color(0xFF1E1B1B)
private val OnBg      = Color.White
private val Muted     = Color(0xFFB0B0B0)
private val Accent    = Color(0xFF7A6BFF)

// --- Datos mock (solo para Capturas y Consolas) ---
data class CaptureTile(
    val id: String,
    val title: String,
    val imageUrl: String
)

data class ConsoleTile(
    val id: String,
    val name: String,
    val imageUrl: String
)

private fun squareUrl(seed: String) = "https://picsum.photos/seed/$seed/600/600"

private val mockCaptures = listOf(
    CaptureTile("c1","Headshot Pro • 10/10", squareUrl("cap_headshot")),
    CaptureTile("c2","Boss vencido", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjhtmGR2aKXpiiqfhqKb2_qPSLHav66eEpZYKYjoyo2xoZAaKxHW27wIXA4bPJ9AN-VCDTVb7-nWQRnoER4Ect6t-KiSsSyC_QFXUCp8S6b4AeO_f3SeZzjZQAR4vqS-6xdNEVXug9JPhA/s1600/Bloodborne%25E2%2584%25A2_20150525225756.jpg"),
    CaptureTile("c3","Construcción épica", squareUrl("cap_build")),
    CaptureTile("c4","Victoria Royale", squareUrl("cap_victory")),
    CaptureTile("c5","Foto del clan", squareUrl("cap_clan")),
    CaptureTile("c6","Paisaje del mapa", squareUrl("cap_landscape")),
    CaptureTile("c7","Auto legendario", squareUrl("cap_car")),
    CaptureTile("c8","Loot SSS", squareUrl("cap_loot")),
    CaptureTile("c9","Combo x120", squareUrl("cap_combo"))
)

private val mockConsoles = listOf(
    ConsoleTile("p5", "PlayStation 5", squareUrl("playstation5")),
    ConsoleTile("xsx", "Xbox Series X", squareUrl("xboxseriesx")),
    ConsoleTile("nsw", "Nintendo Switch", squareUrl("nintendoswitch")),
    ConsoleTile("sd", "Steam Deck", squareUrl("steamdeck")),
    ConsoleTile("nsw2", "Nintendo Switch 2", squareUrl("nintendoswitch2"))
)

// --- UI ---
@Composable
fun MyGamesScreen(
    viewModel: HomeViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(1) } // 0: Capturas, 1: Juegos, 2: Consolas
    val tabs = listOf("Capturas", "Juegos", "Consolas")

    // Juegos desde la API
    val games by viewModel.games.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        containerColor = BgColor,
        topBar = {
            // Header
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

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        SmallRoundIcon(Icons.Filled.FilterList, contentDesc = "Filtrar")
                        SmallRoundIcon(Icons.Filled.Sort, contentDesc = "Ordenar")
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Contador segun la pestaña
                val countText = when (selectedTab) {
                    0 -> "${mockCaptures.size} capturas"
                    1 -> "${games.size} juegos"
                    else -> "${mockConsoles.size} consolas"
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
            // --- Capturas (mock) ---
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

            // --- Juegos (API) ---
            1 -> {
                when {
                    isLoading -> {
                        Box(
                            Modifier
                                .padding(padding)
                                .fillMaxSize()
                                .background(BgColor),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Accent)
                        }
                    }

                    error != null -> {
                        Box(
                            Modifier
                                .padding(padding)
                                .fillMaxSize()
                                .background(BgColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = error ?: "Error al cargar juegos",
                                color = OnBg
                            )
                        }
                    }

                    else -> {
                        LazyVerticalGrid(
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
                            items(games) { game ->
                                GameSquare(game)
                            }
                        }
                    }
                }
            }

            // --- Consolas (mock) ---
            else -> LazyVerticalGrid(
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
                items(mockConsoles) { console ->
                    ConsoleSquare(console)
                }
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

// Ahora GameSquare usa el modelo Games (de la API)
@Composable
private fun GameSquare(game: Games) {
    Column {
        val shape = RoundedCornerShape(14.dp)
        Box(
            modifier = Modifier
                .aspectRatio(1f) // cuadrado
                .clip(shape)
                .background(CardColor)
        ) {
            AsyncImage(
                model = game.image,
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
                .aspectRatio(1f)
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

@Composable
private fun ConsoleSquare(console: ConsoleTile) {
    Column {
        val shape = RoundedCornerShape(14.dp)
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(shape)
                .background(CardColor)
        ) {
            AsyncImage(
                model = console.imageUrl,
                contentDescription = console.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = console.name,
            color = OnBg,
            fontSize = 13.sp,
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
