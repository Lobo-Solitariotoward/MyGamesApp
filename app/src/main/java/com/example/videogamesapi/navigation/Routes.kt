package com.example.videogamesapi.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Profile : Routes("profile")
    object Trending : Routes("trending")
    object Chat : Routes("chat")
    object MyGames : Routes("my_games")
    object Login : Routes("login")
    object Register : Routes("register")
}
