package com.example.videogamesapi.screens

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.videogamesapi.screens.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.videogamesapi.models.Category
import com.example.videogamesapi.models.Games

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val categoriesState by viewModel.categories.collectAsState()
    val trendingGamesState by viewModel.trendingGames.collectAsState()
    val newGamesState by viewModel.newGames.collectAsState()
    val recommendedGamesState by viewModel.recommendedGames.collectAsState()

    val primaryColor = Color(0xFF1E0E4f)

    Scaffold(
        bottomBar = { AppBottomNavigationBar() },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryColor)
                .padding(paddingValues)
        ) {
            HomeHeader()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            CategoryList(
                categories = categoriesState,
                onCategorySelected = viewModel::selectCategory
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "Trending Games")
            TrendingGamesList(
                games = trendingGamesState,
                onGameClicked = { gameId -> /* Acción de navegación */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "New Games")
            NewGamesList(
                games = newGamesState,
                onGameClicked = { gameId -> /* Acción de navegación */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "Recommended For You")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(recommendedGamesState, key = { it.id }) { game ->
                    RecommendedGameCard(game = game, onGameClicked = { gameId -> /* ... */ })
                }
            }
        }

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
            modifier = Modifier.clickable { /* Acción para ver más */ }
        )
    }
}

@Composable
fun HomeHeader() {
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
                .background(Color(0xFF3B298E))
                .padding(8.dp)
                .clickable{}
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back",
                color = Color.LightGray,
                fontSize = 10.sp
            )
            Text(
                text = "@Cat11",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "User",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
    }
}

@Composable
fun CategoryList(
    categories: List<Category>,
    onCategorySelected: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories, key = { it.id }) { category ->
            CategoryItem(
                category = category,
                onClick = { onCategorySelected(category.id) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category, onClick: () -> Unit
){
    val containerColor = if (category.isSelected) Color(0xFF5E3CEF) else Color(0xFF3B298E)
    val contentColor = Color.White

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = containerColor,
        contentColor = contentColor,
        modifier = Modifier
            .height(40.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun TrendingGamesList(games: List<Games>, onGameClicked: (Int) -> Unit){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games, key = { it.id }) { game ->
            TrendingGameCard(game = game, onGameClicked = onGameClicked)
        }
    }
}

@Composable
fun TrendingGameCard(game: Games, onGameClicked: (Int) -> Unit) {
    val cardWidth = 200.dp
    val cardHeight = 300.dp

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        // Usa un color oscuro como fallback
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3B298E)),
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .clickable { onGameClicked(game.id) }
    ) {

        AsyncImage(
            model = game.imageUrl ?: "https://pixelz.cc/wp-content/uploads/2018/06/street-fighter-v-uhd-4k-wallpaper.jpg",
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun NewGamesList(games: List<Games>, onGameClicked: (Int) -> Unit) {
    // Usamos LazyRow para el scroll horizontal, como en el diseño
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(games, key = { it.id }) { game ->
            NewGameItem(game = game, onGameClicked = onGameClicked)
        }
    }
}

@Composable
fun NewGameItem(game: Games, onGameClicked: (Int) -> Unit) {
    val itemWidth = 250.dp

    Row(
        modifier = Modifier
            .width(itemWidth)
            .height(90.dp) // Altura fija para la lista compacta
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF3B298E)) // Fondo oscuro del ítem
            .clickable { onGameClicked(game.id) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen (Póster pequeño)
        AsyncImage(
            model = game.imageUrl ?: "https://wallpapers.com/images/hd/dragon-ball-z-phone-5019gapwfmkg5nb3.jpg",
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(74.dp)
                .clip(RoundedCornerShape(8.dp))
        )


        Spacer(modifier = Modifier.width(10.dp))

        // Detalles del Texto
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = game.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = game.categories,
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
fun AppBottomNavigationBar() {
    // Colores oscuros para simular el diseño
    val navigationBackgroundColor = Color(0xFF3B298E)
    val selectedItemColor = Color(0xFF5E3CEF)
    val unselectedItemColor = Color.LightGray

    // En el diseño, la barra tiene un fondo curvo. Esto se logra con un Surface o Card.
    Surface(
        color = navigationBackgroundColor,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), // Curva superior
        shadowElevation = 8.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent, // Fondo transparente para que se vea el Surface
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            // Ítems de navegación (Home, Search, Camera, Chat, Profile)
            NavigationBarItem(
                selected = true,
                onClick = { /* Navegar a Home */ },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = selectedItemColor) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent, // Quitamos el indicador predeterminado
                    selectedIconColor = selectedItemColor,
                    unselectedIconColor = unselectedItemColor
                )
            )
            NavigationBarItem(
                selected = false,
                onClick = { /* Navegar a Search */ },
                icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            NavigationBarItem(
                selected = false,
                onClick = { /* Navegar a Camera */ },
                icon = { Icon(Icons.Filled.Videocam, contentDescription = "Camera") },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            NavigationBarItem(
                selected = false,
                onClick = { /* Navegar a Chat */ },
                icon = { Icon(Icons.Filled.ChatBubble, contentDescription = "Chat") },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            NavigationBarItem(
                selected = false,
                onClick = { /* Navegar a Profile */ },
                icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}

@Composable
fun RecommendedGameCard(game: Games, onGameClicked: (Int) -> Unit) {
    val cardSize = 120.dp

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3B298E)),
        modifier = Modifier
            .size(cardSize)
            .clickable { onGameClicked(game.id) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen (pequeña y cuadrada)
            AsyncImage(
                model = game.imageUrl ?: "https://pixelz.cc/wp-content/uploads/2018/06/street-fighter-v-uhd-4k-wallpaper.jpg",
                contentDescription = game.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardSize * 0.7f)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            // Título
            Text(
                text = game.title,
                color = Color.White,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen() // no usar HomeViewModel() directamente aquí
}