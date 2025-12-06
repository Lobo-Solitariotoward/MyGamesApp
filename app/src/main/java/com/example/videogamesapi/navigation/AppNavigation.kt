package com.example.videogamesapi.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videogamesapi.screens.*
import com.example.videogamesapi.navigation.Routes

@Composable
fun AppNavigation(navController: NavHostController) {
    //  ESTADOS: Usuario registrado (se guarda en RAM)
    var registeredEmail by rememberSaveable { mutableStateOf<String?>(null) }
    var registeredPassword by rememberSaveable { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        //  LOGIN SCREEN
        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Routes.Register.route)
                },
                registeredEmail = registeredEmail,
                registeredPassword = registeredPassword
            )
        }
        // REGISTER SCREEN
        composable(Routes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { email, pass ->
                    // Guardar datos del usuario
                    registeredEmail = email
                    registeredPassword = pass

                    // Regresar a Login
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }
        // MAIN SCREENS
        composable(Routes.Home.route) { HomeScreen(navController) }
        composable(Routes.MyGames.route) { MyGamesScreen(navController) }
        composable(Routes.Gender.route) { ExploreScreen(navController) }
        composable(Routes.Trending.route) { TrendingScreen(navController) }
        composable(Routes.Chat.route) { ChatScreen(navController) }
        composable(Routes.Profile.route) { ProfileScreen(navController) }
        composable("detail_screen/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailScreen(navController, id)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppNavigationPreview() {
    val navController = rememberNavController()
    AppNavigation(navController)
}
