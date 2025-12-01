package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.screens.viewmodel.HomeViewModel

// Paleta de colores
private val BgColor = Color(0xFF0C0F27)
private val Accent = Color(0xFF7A6BFF)
private val OnBg = Color.White
private val Muted = Color(0xFFB0B0B0)
private val BottomBar = Color(0xFF1E1B1B)

// UI
@Composable
fun ExploreScreen(
    genreTitle: String = "Aventura",
    onBack: () -> Unit = {},
    onSearch: () -> Unit = {},
    onCast: () -> Unit = {},
    onSeeAll: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val scroll = rememberScrollState()

    // Juegos desde el ViewModel
    val games by viewModel.games.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Derivar secciones a partir de la lista
    val topBanners = games.take(3)
    val newReleases = games.take(8) // por ejemplo, primeros 8
    val action = games.filter { it.gender.contains("acción", ignoreCase = true) }

    Scaffold(
        topBar = {
            TopBar(
                genreTitle = genreTitle,
                onBack = onBack,
                onSearch = onSearch,
                onCast = onCast
            )
        },
        bottomBar = { BottomNavBar() },
        containerColor = BgColor
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgColor)
                .verticalScroll(scroll)
        ) {
            when {
                isLoading -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CircularProgressIndicator(color = Accent)
                    }
                }

                error != null -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            text = error ?: "Ocurrió un error",
                            color = OnBg
                        )
                    }
                }

                games.isEmpty() -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text("No hay juegos disponibles", color = OnBg)
                    }
                }

                else -> {
                    Spacer(Modifier.height(8.dp))

                    // Carrusel superior
                    if (topBanners.isNotEmpty()) {
                        BannerRow(items = topBanners)
                    }

                    // Sección "Nuevo"
                    SectionHeader(
                        title = "Nuevo",
                        actionText = "Ver todo"
                    ) { onSeeAll("Nuevo") }

                    PosterRow(items = newReleases)

                    // Sección "Acción"
                    if (action.isNotEmpty()) {
                        SectionHeader(
                            title = "Acción",
                            actionText = "Ver todo"
                        ) { onSeeAll("Acción") }

                        PosterRow(items = action)
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    genreTitle: String,
    onBack: () -> Unit,
    onSearch: () -> Unit,
    onCast: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Atrás", tint = OnBg)
            }
        },
        title = {
            Column {
                Text("Géneros", fontSize = 12.sp, color = Muted)
                Text(genreTitle, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = OnBg)
            }
        },
        actions = {
            IconButton(onClick = onSearch) {
                Icon(Icons.Rounded.Search, contentDescription = "Buscar", tint = OnBg)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BgColor,
            navigationIconContentColor = OnBg,
            titleContentColor = OnBg,
            actionIconContentColor = OnBg
        )
    )
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = OnBg
        )
        Spacer(Modifier.weight(1f))
        if (actionText != null && onAction != null) {
            TextButton(
                onClick = onAction,
                colors = ButtonDefaults.textButtonColors(contentColor = Accent)
            ) { Text(actionText, fontWeight = FontWeight.Medium) }
        }
    }
}

@Composable
private fun BannerRow(items: List<Games>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { game ->
            BannerCard(game)
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun BannerCard(game: Games) {
    val shape = MaterialTheme.shapes.medium
    Column(
        modifier = Modifier
            .width(280.dp)
            .clip(shape)
    ) {
        AsyncImage(
            model = game.image,
            contentDescription = game.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(shape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = game.title,
            modifier = Modifier.padding(horizontal = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            color = OnBg
        )
        Text(
            text = "${game.developer} • ${game.gender}",
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Muted,
            fontSize = 12.sp
        )
        Spacer(Modifier.height(6.dp))
    }
}

@Composable
private fun PosterRow(items: List<Games>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(items) { game -> PosterCard(game) }
    }
}

@Composable
private fun PosterCard(game: Games) {
    Column(
        modifier = Modifier.width(160.dp)
    ) {
        val shape = MaterialTheme.shapes.medium
        AsyncImage(
            model = game.image,
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .clip(shape)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = game.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            color = OnBg
        )
        Text(
            text = "${game.developer} • ★ ${game.rating}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Muted,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun BottomNavBar() {
    NavigationBar(
        containerColor = BottomBar
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Text("🏠") },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnBg,
                selectedTextColor = OnBg,
                unselectedIconColor = Muted,
                unselectedTextColor = Muted,
                indicatorColor = Accent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("🔖") },
            label = { Text("Mis Listas") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnBg,
                selectedTextColor = OnBg,
                unselectedIconColor = Muted,
                unselectedTextColor = Muted,
                indicatorColor = Accent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("🟧") },
            label = { Text("Explorar") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnBg,
                selectedTextColor = OnBg,
                unselectedIconColor = Muted,
                unselectedTextColor = Muted,
                indicatorColor = Accent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("✨") },
            label = { Text("Simulcast") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnBg,
                selectedTextColor = OnBg,
                unselectedIconColor = Muted,
                unselectedTextColor = Muted,
                indicatorColor = Accent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Text("👤") },
            label = { Text("Cuenta") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnBg,
                selectedTextColor = OnBg,
                unselectedIconColor = Muted,
                unselectedTextColor = Muted,
                indicatorColor = Accent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {

    ExploreScreen()
}
