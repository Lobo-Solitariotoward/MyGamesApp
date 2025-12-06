package com.example.videogamesapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.videogamesapi.navigation.AppNavigation
import com.example.videogamesapi.ui.theme.VideogamesapiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VideogamesapiTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
