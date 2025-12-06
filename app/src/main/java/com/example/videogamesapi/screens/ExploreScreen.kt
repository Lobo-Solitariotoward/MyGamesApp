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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.screens.viewmodel.HomeViewModel
import com.example.videogamesapi.utils.safeImageUrl
import com.example.videogamesapi.utils.safeCategories
import com.example.videogamesapi.utils.safeTitle
import com.example.videogamesapi.screens.AppBottomNavigationBar

// 🎨 Paleta
private val BgColor = Color(0xFF0C0F27)
private val Accent = Color(0xFF7A6BFF)
private val OnBg = Color.White
private val Muted = Color(0xFFB0B0B0)

@Composable
fun ExploreScreen(
    navController: NavController,
    genreTitle: String = "Lo más vendido",
    onBack: () -> Unit = {},
    onSearch: () -> Unit = {},
    onCast: () -> Unit = {},
    onSeeAll: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val scroll = rememberScrollState()
    val games by viewModel.games.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val gamesByGenre = games.groupBy { it.gender.ifBlank { "Otros" } }

    val orderedGenres = buildList {
        if (gamesByGenre.containsKey(genreTitle)) add(genreTitle)
        addAll(
            gamesByGenre.keys.filter { it != genreTitle }.sorted()
        )
    }

    val topBanners =
        (gamesByGenre[genreTitle].orEmpty().take(3))
            .ifEmpty { games.take(3) }

    Scaffold(
        topBar = {
            TopBar(
                genreTitle = genreTitle,
                onBack = onBack,
                onSearch = onSearch,
                onCast = onCast
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                navController = navController,
                navigationColor = Color(0xFF1E1B1B)
            )
        },
        containerColor = BgColor
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BgColor)
                .verticalScroll(scroll)
                .padding(bottom = 90.dp) // evita que el menú tape contenido
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
                        Text(error ?: "Ocurrió un error", color = OnBg)
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

                    if (topBanners.isNotEmpty()) {
                        BannerRow(items = topBanners)
                    }

                    orderedGenres.forEach { genre ->
                        val list = gamesByGenre[genre].orEmpty()
                        if (list.isNotEmpty()) {
                            SectionHeader(
                                title = genre,
                                actionText = "Ver todo"
                            ) {
                                onSeeAll(genre)
                            }
                            PosterRow(items = list)
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

/* ---------------------- UI COMPONENTS ---------------------- */

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
            titleContentColor = OnBg,
            navigationIconContentColor = OnBg,
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
            TextButton(onClick = onAction) {
                Text(actionText, color = Accent, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun BannerRow(items: List<Games>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { game -> BannerCard(game) }
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
            model = game.safeImageUrl(),
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
            model = game.safeImageUrl(),
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