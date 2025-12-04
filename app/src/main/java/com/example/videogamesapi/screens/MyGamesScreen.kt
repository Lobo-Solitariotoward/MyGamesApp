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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.screens.viewmodel.MyGamesViewModel
import com.example.videogamesapi.R


// Paleta
private val BgColor   = Color(0xFF0C0F27)
private val CardColor = Color(0xFF1E1B1B)
private val OnBg      = Color.White
private val Muted     = Color(0xFFB0B0B0)
private val Accent    = Color(0xFF7A6BFF)

// --- Datos mock (solo para Capturas y Consolas)
data class CaptureTile(
    val id: String,
    val title: String,
    val imageUrl: String
)

data class ConsoleTile(
    val id: String,
    val name: String,
   @DrawableRes val imageRes: Int
)

private fun squareUrl(seed: String) = "https://picsum.photos/seed/$seed/600/600"

private val mockCaptures = listOf(
    CaptureTile("c1","Headshot Pro • 10/10", squareUrl("https://www.callofduty.com/content/dam/atvi/callofduty/cod-touchui/legacy/wwii/features/multiplayer/CoD_WWII_Launch_MP_01_wm.png")),
    CaptureTile("c2","Boss vencido", "https://oyster.ignimgs.com/mediawiki/apis.ign.com/stellar-blade/3/3e/Stellar_Blade_20240423020811.jpg"),
    CaptureTile("c3","Construcción épica", squareUrl("https://i.ytimg.com/vi/4jwYtfvBjhU/maxresdefault.jpg")),
    CaptureTile("c4","Victoria Royale", squareUrl("https://cdn.mos.cms.futurecdn.net/snabQjTr66r3GPVAfPNqnH.jpg")),
    CaptureTile("c5","Foto del clan", squareUrl("https://blz-contentstack-images.akamaized.net/v3/assets/blt2477dcaf4ebd440c/bltdabc3782553659f1/6785b50a1970a9f14eb5ccd7/xboxshowcase.png")),
    CaptureTile("c6","Paisaje del mapa", squareUrl("https://www.theloadout.com/wp-content/uploads/2022/02/elden-ring-review-1.jpg")),
    CaptureTile("c7","Auto legendario", squareUrl("https://media.moddb.com/images/mods/1/35/34792/7-2-1404842988.jpg")),
    CaptureTile("c8","Loot SSS", squareUrl("https://staticctf.ubisoft.com/J3yJr34U2pZ2Ieem48Dwy9uqj5PNUQTn/4CdAD2JX2wqu1ytWiPxP3j/8e20767528333b13e640611147069018/-ACII-_Screenshots_-_5.jpg")),
    CaptureTile("c9","Combo x120", squareUrl("https://staticg.sportskeeda.com/editor/2022/09/123b7-16621326050136-1920.jpg"))
)

private val mockConsoles = listOf(
    ConsoleTile("p5", "PlayStation 5", R.drawable.ps5),
    ConsoleTile("xsx", "Xbox Series X", R.drawable.xsx),
    ConsoleTile("nsw", "Nintendo Switch", R.drawable.ns1),
    ConsoleTile("sd", "Steam Deck", R.drawable.stm),
    ConsoleTile("nsw2", "Nintendo Switch 2", R.drawable.ns2)
)

// --- UI ---
@Composable
fun MyGamesScreen(
    viewModel: MyGamesViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(1) } // 0: Capturas, 1: Juegos, 2: Consolas
    val tabs = listOf("Capturas", "Juegos", "Consolas")


    val state = viewModel.uiState
    val games = state.games
    val isLoading = state.isLoading
    val error = state.errorMessage

    Scaffold(
        containerColor = BgColor,
        topBar = {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgColor)
                    .padding(horizontal = 16.dp, vertical = 50.dp)
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

                // Contador según la pestaña
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
            // Capturas (mock)
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

            // Juegos (API)
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

            // Consolas (mock)
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

// Card de juego usando Games del API
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

@Preview(showBackground = true)
@Composable
private fun MyGamesScreenPreview() {
    MyGamesScreen()
}
