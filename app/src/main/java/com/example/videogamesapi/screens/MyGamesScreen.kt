package com.example.videogamesapi.screens

import androidx.annotation.DrawableRes
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.screens.viewmodel.MyGamesViewModel
import com.example.videogamesapi.R
import com.example.videogamesapi.navigation.Routes


// Paleta
private val BgColor   = Color(0xFF0C0F27)
private val CardColor = Color(0xFF1E1B1B)
private val OnBg      = Color.White
private val Muted     = Color(0xFFB0B0B0)
private val Accent    = Color(0xFF7A6BFF)


// --- MOCK DATA
data class CaptureTile(val id: String, val title: String, val imageUrl: String)
data class ConsoleTile(val id: String, val name: String, @DrawableRes val imageRes: Int)

private fun squareUrl(seed: String) = seed

private val mockCaptures = listOf(
    CaptureTile("c1","Headshot Pro • 10/10", squareUrl("https://i.imgur.com/T0oja72.jpeg")),
    CaptureTile("c2","Boss vencido", "https://i.imgur.com/ZqYwz8T.jpeg"),
    CaptureTile("c3","Construcción épica", "https://i.imgur.com/7eY7QmF.jpeg"),
    CaptureTile("c4","Victoria Royale", "https://i.imgur.com/8w0QpFZ.jpeg")
)

private val mockConsoles = listOf(
    ConsoleTile("p5", "PlayStation 5", R.drawable.ps5),
    ConsoleTile("xsx", "Xbox Series X", R.drawable.xsx),
    ConsoleTile("nsw", "Nintendo Switch", R.drawable.ns1),
    ConsoleTile("sd", "Steam Deck", R.drawable.stm)
)
@Composable
fun MyGamesScreen(
    navController: NavController,
    viewModel: MyGamesViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(1) } // 0 Capturas - 1 Juegos - 2 Consolas
    val tabs = listOf("Capturas", "Juegos", "Consolas")

    val state = viewModel.uiState
    val games = state.games
    val isLoading = state.isLoading
    val error = state.errorMessage

    Scaffold(
        containerColor = BgColor,

        // AQUÍ IMPLEMENTAMOS LA BARRA DE NAVEGACIÓN
        bottomBar = {
            AppBottomNavigationBar(
                navController = navController,
                navigationColor = Color(0xFF1E1B1B)
            )
        }
    ) { padding ->

        // =============================================
        //                TOP HEADER
        // =============================================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BgColor)
                .padding(horizontal = 16.dp)
                .padding(top = 50.dp, bottom = 10.dp)
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
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SmallRoundIcon(Icons.Filled.FilterList, "Filtrar")
                    SmallRoundIcon(Icons.Filled.Sort, "Ordenar")
                }
            }

            Spacer(Modifier.height(10.dp))

            val countText = when (selectedTab) {
                0 -> "${mockCaptures.size} capturas"
                1 -> "${games.size} juegos"
                else -> "${mockConsoles.size} consolas"
            }

            Text(
                text = countText,
                color = OnBg,
                fontSize = 18.sp
            )
        }

        when (selectedTab) {

            // Capturas (mock)
            0 -> LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 10.dp)
                    .background(BgColor)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 90.dp), // evita tapar con navbar
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mockCaptures) { cap ->
                    CaptureSquare(cap)
                }
            }

            // Juegos
            1 -> when {
                isLoading -> Box(
                    Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Accent)
                }

                error != null -> Box(
                    Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(error ?: "Error al cargar juegos", color = OnBg)
                }

                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = 10.dp)
                        .background(BgColor)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 90.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(games) { game ->
                        GameSquare(game)
                    }
                }
            }

            // Consolas
            2 -> LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 10.dp)
                    .background(BgColor)
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 90.dp),
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
private fun SmallRoundIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDesc: String
) {
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

//  Game Card (API)
@Composable
private fun GameSquare(game: Games) {
    Column {
        val shape = RoundedCornerShape(14.dp)
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(shape)
                .background(CardColor)
        ) {
            AsyncImage(
                model = game.image,
                contentDescription = game.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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

//   Capture item (mock)
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

//   Console item (mock)
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
                model = console.imageRes,
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
