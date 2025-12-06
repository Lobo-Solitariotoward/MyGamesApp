package com.example.videogamesapi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videogamesapi.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        // -------- AUTH --------
        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Routes.Register.route)
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // -------- MAIN --------
        composable(Routes.Home.route) { HomeScreen(navController) }
        composable(Routes.MyGames.route) { MyGamesScreen(navController) }
        composable(Routes.Gender.route) { ExploreScreen(navController) }
        composable(Routes.Trending.route) { TrendingScreen(navController) }
        composable(Routes.Chat.route) { ChatScreen(navController) }
        composable(Routes.Profile.route) { ProfileScreen(navController) }
        composable(route = "detail_screen/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailScreen(navController, gameId = id)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppNavigationPreview() {
    val navController = rememberNavController()
    AppNavigation(navController)
}
