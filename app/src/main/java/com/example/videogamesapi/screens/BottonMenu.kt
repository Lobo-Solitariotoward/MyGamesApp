package com.example.videogamesapi.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.videogamesapi.navigation.Routes

@Composable
fun BottomMenu(navController: NavController, selectedItem: String = "Home") {

    fun go(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.startDestinationId) { saveState = true }
        }
    }

    NavigationBar(
        containerColor = Color(0xFF1A182E),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedItem == "Home",
            onClick = { go(Routes.Home.route) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White) }
        )
        NavigationBarItem(
            selected = selectedItem == "MyGames",
            onClick = { go(Routes.MyGames.route) },
            icon = { Icon(Icons.Filled.SportsEsports, contentDescription = "My Games", tint = Color.White) }
        )
        NavigationBarItem(
            selected = selectedItem == "Gender",
            onClick = { go(Routes.Gender.route) },
            icon = { Icon(Icons.Filled.Category, contentDescription = "Genres", tint = Color.White) }
        )
        NavigationBarItem(
            selected = selectedItem == "Trending",
            onClick = { go(Routes.Trending.route) },
            icon = { Icon(Icons.Filled.Whatshot, contentDescription = "Trending", tint = Color.White) }
        )
        NavigationBarItem(
            selected = selectedItem == "Chat",
            onClick = { go(Routes.Chat.route) },
            icon = { Icon(Icons.Filled.Chat, contentDescription = "Chat", tint = Color.White) }
        )
        NavigationBarItem(
            selected = selectedItem == "Profile",
            onClick = { go(Routes.Profile.route) },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.White) }
        )
    }
}
