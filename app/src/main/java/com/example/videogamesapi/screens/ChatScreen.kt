package com.example.videogamesapi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter

data class ChatMessage(
    val username: String,
    val text: String,
    val isMine: Boolean,
    val profileUrl: String,
    val isOnline: Boolean
)

@Composable
fun ChatScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Fondo con imagen y oscurecimiento
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    "https://www.xtrafondos.com/wallpapers/vertical/chica-anime-en-paisaje-3730.jpg"
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0x66000000), Color(0xCC000000))
                        )
                    )
            )

            // Encabezado del chat
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Community Chat 💬",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen()
}
