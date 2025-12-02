package com.example.videogamesapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.videogamesapi.screens.ExploreScreen
import com.example.videogamesapi.screens.HomeScreen
import com.example.videogamesapi.screens.HomeScreenPreview
//import com.example.videogamesapi.screens.HomeScreen
import com.example.videogamesapi.screens.MyGamesScreen
import com.example.videogamesapi.ui.theme.VideogamesapiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreenPreview()
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    VideogamesapiTheme {
//        Greeting("Android")
//    }
//}