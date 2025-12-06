package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.fallback
import coil3.request.placeholder
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.navigation.Routes
import com.example.videogamesapi.screens.viewmodel.HomeViewModel
import com.example.videogamesapi.R

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val games by viewModel.games.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val error by viewModel.error.collectAsState(initial = null)

    val primaryColor = Color(0xFF1E0E4F)
    val cardColor = Color(0xFF3B298E)
    val navColor = Color(0xFF3B298E).copy(alpha = 0.95f)

    Scaffold(
        bottomBar = {
            AppBottomNavigationBar(navController = navController, navigationColor = navColor)
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 70.dp)
        ) {

            HomeHeader(cardColor)

            Spacer(Modifier.height(16.dp))

            when {
                isLoading -> Loading()
                error != null -> ErrorMessage(error)
                else -> {
                    SectionHeader("Categories")
                    CategoryPlaceholders(cardColor)

                    Spacer(Modifier.height(16.dp))

                    SectionHeader("Trending Games")
                    TrendingGamesList(games, navController, cardColor)

                    Spacer(Modifier.height(16.dp))

                    SectionHeader("New Games")
                    NewGamesList(games, navController, cardColor)

                    Spacer(Modifier.height(120.dp))
                }
            }
        }
    }
}
@Composable
fun AppBottomNavigationBar(navController: NavController, navigationColor: Color) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.Home.route

    val selected = Color(0xFF5E3CEF)
    val unselected = Color.LightGray

    Surface(
        color = navigationColor,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 12.dp
    ) {
        NavigationBar(containerColor = Color.Transparent) {

            fun go(route: String) {
                if (route != currentRoute) {
                    navController.navigate(route) { launchSingleTop = true }
                }
            }

            NavigationBarItem(
                selected = currentRoute == Routes.Home.route,
                onClick = { go(Routes.Home.route) },
                icon = {
                    Icon(Icons.Default.Home, null,
                        tint = if (currentRoute == Routes.Home.route) selected else unselected)
                }
            )

            NavigationBarItem(
                selected = currentRoute == Routes.MyGames.route,
                onClick = { go(Routes.MyGames.route) },
                icon = {
                    Icon(Icons.Default.Search, null,
                        tint = if (currentRoute == Routes.MyGames.route) selected else unselected)
                }
            )

            NavigationBarItem(
                selected = currentRoute == Routes.Trending.route,
                onClick = { go(Routes.Trending.route) },
                icon = {
                    Icon(Icons.Default.LocalFireDepartment, null,
                        tint = if (currentRoute == Routes.Trending.route) selected else unselected)
                }
            )

            NavigationBarItem(
                selected = currentRoute == Routes.Chat.route,
                onClick = { go(Routes.Chat.route) },
                icon = {
                    Icon(Icons.Default.ChatBubble, null,
                        tint = if (currentRoute == Routes.Chat.route) selected else unselected)
                }
            )

            NavigationBarItem(
                selected = currentRoute == Routes.Profile.route,
                onClick = { go(Routes.Profile.route) },
                icon = {
                    Icon(Icons.Default.Person, null,
                        tint = if (currentRoute == Routes.Profile.route) selected else unselected)
                }
            )
        }
    }
}
@Composable
fun Loading() {
    Column(
        Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.White)
        Spacer(Modifier.height(8.dp))
        Text("Cargando juegos...", color = Color.LightGray)
    }
}

@Composable
fun ErrorMessage(error: String?) {
    Text(
        text = error ?: "Error desconocido",
        color = Color.Red,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun TrendingGamesList(games: List<Games>, nav: NavController, cardColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games) { game ->
            TrendingGameCard(game, cardColor) {
                nav.navigate("detail_screen/${game.id}")
            }
        }
    }
}

@Composable
fun NewGamesList(games: List<Games>, nav: NavController, itemColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games) { game ->
            NewGameItem(game, itemColor) {
                nav.navigate("detail_screen/${game.id}")
            }
        }
    }
}

@Composable
fun TrendingGameCard(game: Games, cardColor: Color, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .clickable { onClick() }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(game.safeImageUrl())
                .crossfade(true)
                .size(512, 512)     // evita crash y permite fallback
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .placeholder(R.drawable.back_image)
                .error(R.drawable.back_image)
                .fallback(R.drawable.back_image)
                .build(),
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NewGameItem(game: Games, cardColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(260.dp)
            .height(90.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(cardColor)
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(game.safeImageUrl())
                .crossfade(true)
                .size(256, 256)
                .placeholder(R.drawable.back_image)
                .error(R.drawable.back_image)
                .fallback(R.drawable.back_image)
                .build(),
            contentDescription = game.title,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(10.dp))

        Column {
            Text(game.title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                game.developer ?: "",
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
@Composable
fun HomeHeader(cardColor: Color) {
    Row(
        Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Search, null,
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(cardColor)
                .padding(8.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("welcome back", color = Color.LightGray, fontSize = 10.sp)
            Text("@cat11", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        Icon(
            Icons.Default.Person, null,
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.DarkGray)
        )
    }
}
@Composable
fun CategoryPlaceholders(cardColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOf("Action", "Shooter", "MOBA")) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = cardColor,
                modifier = Modifier
                    .height(40.dp)
                    .clickable { }
            ) {
                Text(
                    text = it,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
fun Games.safeImageUrl(): String {
    if (!this.image.isNullOrBlank() &&
        (image.startsWith("http://") || image.startsWith("https://"))
    ) {
        return this.image
    }

    // 🔥 Imagen de respaldo
    return "https://i.postimg.cc/MKTKzH0G/game-placeholder.png"
}
