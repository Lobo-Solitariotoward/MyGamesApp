package com.example.videogamesapi.navigation

// Clase sellada que define todas las rutas principales de la app
sealed class Routes(val route: String) {
    object Login : Routes("login")            // Pantalla de inicio de sesión

    object Register : Routes("register")
    object Home : Routes("home")              // Pantalla principal
    object MyGames : Routes("mygames")        // Mis juegos
    object Gender : Routes("gender")          // Géneros de videojuegos
    object Trending : Routes("trending")      // Tendencias
    object Chat : Routes("chat")              // Chat comunitario
    object Profile : Routes("profile")        // Perfil de usuario
}
