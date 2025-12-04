package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.navigation.Routes // Importa tus rutas
import com.example.videogamesapi.screens.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    // HomeScreen necesita el NavController para manejar toda la navegación.
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val gamesState by viewModel.games.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val error by viewModel.error.collectAsState(initial = null)

    val primaryColor = Color(0xFF1E0E4f)
    val cardColor = Color(0xFF3B298E)
    val navigationColor = Color(0xFF3B298E).copy(alpha = 0.9f)

    Scaffold(
        // Pasamos el navController a la barra inferior para manejar la navegación entre rutas
        bottomBar = { AppBottomNavigationBar(navController = navController, navigationColor = navigationColor) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            HomeHeader(cardColor = cardColor)

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Cargando juegos...", color = Color.LightGray)
                    }
                }
                error != null -> {
                    Text(
                        text = error ?: "Error desconocido",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    SectionHeader(title = "Categories")
                    CategoryPlaceholders(cardColor = cardColor)

                    Spacer(modifier = Modifier.height(12.dp))

                    // 1. SECCIÓN: Trending Games
                    SectionHeader(title = "Trending Games")
                    TrendingGamesSection(
                        games = gamesState,
                        // Al hacer click, navegamos a la pantalla de Detalle, asumiendo una ruta
                        onGameClicked = { gameId ->
                            // Asumimos que la ruta de detalle es "detail_screen/{gameId}"
                            navController.navigate("detail_screen/$gameId")
                        },
                        cardColor = cardColor
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 2. SECCIÓN: New Games
                    SectionHeader(title = "New Games")
                    NewGamesSection(
                        games = gamesState,
                        onGameClicked = { gameId ->
                            // Navegamos a la pantalla de Detalle
                            navController.navigate("detail_screen/$gameId")
                        },
                        itemColor = cardColor
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 3. SECCIÓN: Latest Game News (Placeholder)
                    SectionHeader(title = "Latest Game News")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(cardColor.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ChatBubble, contentDescription = "News", tint = Color.LightGray)
                            Text(
                                text = "News articles placeholder",
                                color = Color.LightGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun AppBottomNavigationBar(navController: NavController, navigationColor: Color) {
    val selectedItemColor = Color(0xFF5E3CEF)
    val unselectedItemColor = Color.LightGray
    val currentRoute = navController.currentDestination?.route ?: Routes.Home.route

    Surface(
        color = navigationColor,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 8.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            // Home
            NavigationBarItem(
                selected = currentRoute == Routes.Home.route,
                onClick = { navController.navigate(Routes.Home.route) },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = if (currentRoute == Routes.Home.route) selectedItemColor else unselectedItemColor) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            // Search/Explore (usaremos la ruta MyGames como placeholder visual)
            NavigationBarItem(
                selected = currentRoute == Routes.MyGames.route,
                onClick = { navController.navigate(Routes.MyGames.route) },
                icon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = if (currentRoute == Routes.MyGames.route) selectedItemColor else unselectedItemColor) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            // Trending
            NavigationBarItem(
                selected = currentRoute == Routes.Trending.route,
                onClick = { navController.navigate(Routes.Trending.route) },
                icon = { Icon(Icons.Default.LocalFireDepartment, contentDescription = "Trending", tint = if (currentRoute == Routes.Trending.route) selectedItemColor else unselectedItemColor) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            // Chat
            NavigationBarItem(
                selected = currentRoute == Routes.Chat.route,
                onClick = { navController.navigate(Routes.Chat.route) },
                icon = { Icon(Icons.Filled.ChatBubble, contentDescription = "Chat", tint = if (currentRoute == Routes.Chat.route) selectedItemColor else unselectedItemColor) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            // Profile
            NavigationBarItem(
                selected = currentRoute == Routes.Profile.route,
                onClick = { navController.navigate(Routes.Profile.route) },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = if (currentRoute == Routes.Profile.route) selectedItemColor else unselectedItemColor) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}



@Composable
fun TrendingGamesSection(games: List<Games>, onGameClicked: (String) -> Unit, cardColor: Color) {
    if (games.isEmpty()) {
        Box(modifier = Modifier.height(300.dp)) {
            TrendingEmptyRow(cardColor = cardColor)
        }
    } else {
        TrendingGamesList(games = games, onGameClicked = onGameClicked, cardColor = cardColor)
    }
}

@Composable
fun NewGamesSection(games: List<Games>, onGameClicked: (String) -> Unit, itemColor: Color) {
    if (games.isEmpty()) {
        Box(modifier = Modifier.height(90.dp)) {
            NewGamesEmptyRow(cardColor = itemColor)
        }
    } else {
        NewGamesList(games = games, onGameClicked = onGameClicked, itemColor = itemColor)
    }
}

@Composable
fun TrendingGamesList(games: List<Games>, onGameClicked: (String) -> Unit, cardColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games, key = { it.id }) { game ->
            TrendingGameCard(game = game, onGameClicked = onGameClicked, cardColor = cardColor)
        }
    }
}

@Composable
fun TrendingGameCard(game: Games, onGameClicked: (String) -> Unit, cardColor: Color) {
    val cardWidth = 200.dp
    val cardHeight = 300.dp

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .clickable {
                val gameId = tryInvokeStringOrCollection(game, listOf("id", "getId")) ?: ""
                onGameClicked(gameId)
            }
    ) {
        AsyncImage(
            model = game.safeImageUrl(),
            contentDescription = safeTitle(game),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NewGamesList(games: List<Games>, onGameClicked: (String) -> Unit, itemColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games, key = { it.id }) { game ->
            NewGameItem(game = game, onGameClicked = onGameClicked, itemColor = itemColor)
        }
    }
}

@Composable
fun NewGameItem(game: Games, onGameClicked: (String) -> Unit, itemColor: Color) {
    val itemWidth = 250.dp

    Row(
        modifier = Modifier
            .width(itemWidth)
            .height(90.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(itemColor)
            .clickable {
                val gameId = tryInvokeStringOrCollection(game, listOf("id", "getId")) ?: ""
                onGameClicked(gameId)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = game.safeImageUrl(),
            contentDescription = safeTitle(game),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(74.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = safeTitle(game),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = game.safeCategories(),
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TrendingEmptyRow(cardColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) {
            TrendingEmptyCard(cardColor = cardColor)
        }
    }
}

@Composable
fun TrendingEmptyCard(cardColor: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) { }
    }
}

@Composable
fun NewGamesEmptyRow(cardColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(3) {
            NewGameEmptyItem(cardColor = cardColor)
        }
    }
}

@Composable
fun NewGameEmptyItem(cardColor: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor.copy(alpha = 0.5f)),
        modifier = Modifier
            .width(250.dp)
            .height(90.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) { }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.labelMedium,
            color = Color.LightGray.copy(alpha = 0.7f),
            modifier = Modifier.clickable { }
        )
    }
}

@Composable
fun HomeHeader(cardColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(cardColor)
                .padding(8.dp)
                .clickable { }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "welcome back", color = Color.LightGray, fontSize = 10.sp)
            Text(text = "@cat11", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User",
            tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }
}

@Composable
fun CategoryPlaceholders(cardColor: Color) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOf("Action", "Shooter", "MOBA")) { title ->
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = cardColor,
                modifier = Modifier
                    .height(40.dp)
                    .clickable { }
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


private fun Games.safeImageUrl(): String {
    val candidates = listOf(
        "imageUrl", "getImageUrl", "image", "getImage",
        "thumbnail", "getThumbnail", "avatar", "getAvatar",
        "url", "getUrl"
    )
    return tryInvokeStringOrCollection(this, candidates) ?: ""
}

private fun Games.safeCategories(): String {
    val candidates = listOf(
        "categories", "getCategories", "category", "getCategory",
        "genres", "getGenres", "tags", "getTags"
    )
    return tryInvokeStringOrCollection(this, candidates) ?: ""
}

private fun safeTitle(game: Games): String {
    val titleCandidates = listOf("title", "getTitle", "name", "getName", "nombre", "getNombre")
    val title = tryInvokeStringOrCollection(game, titleCandidates)
    return title?.takeIf { it.isNotBlank() } ?: game.id.toString()
}

private fun tryInvokeStringOrCollection(target: Any, names: List<String>): String? {
    val clazz = target::class.java
    for (n in names) {
        try {
            val field = clazz.declaredFields.firstOrNull { it.name.equals(n, ignoreCase = true) }
            if (field != null) {
                field.isAccessible = true
                val value = field.get(target) ?: continue
                return when (value) {
                    is String -> value
                    is Collection<*> -> value.joinToString(", ") { it.toString() }
                    is Array<*> -> value.joinToString(", ") { it.toString() }
                    else -> value.toString()
                }
            }
            val method = clazz.methods.firstOrNull { it.name.equals(n, ignoreCase = true) && it.parameterCount == 0 }
            if (method != null) {
                val res = method.invoke(target) ?: continue
                return when (res) {
                    is String -> res
                    is Collection<*> -> res.joinToString(", ") { it.toString() }
                    is Array<*> -> res.joinToString(", ") { it.toString() }
                    else -> res.toString()
                }
            }
        } catch (_: Exception) { /* ignore */ }
    }
    return null
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}