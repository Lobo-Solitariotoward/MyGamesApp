package com.example.videogamesapi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun TrendingScreen(onBackClick: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0C0F27), Color(0xFF1A182E))
                )
            )
    ) {
        // 🔹 Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 14.dp)
        ) {
            IconButton(onClick = { onBackClick?.invoke() }, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "🔥 Trending Now",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // 🔹 Contenido desplazable
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 📰 Noticia destacada
            HighlightCard(
                imageUrl = "https://www.godisageek.com/wp-content/uploads/Elden-Ring-Shadow-of-the-Erdtree-review.jpg",
                title = "Elden Ring: Shadow of the Erdtree",
                description = "El DLC más esperado del año ya está disponible. Los jugadores están maravillados por su mundo y dificultad épica."
            )

            // 🧩 Fila con dos noticias secundarias
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallCard(
                    imageUrl = "https://cdn.sanity.io/images/dsfx7636/news_live/bb4f11049e5275ce32aa280ee2b1ef22398d3257-1920x1080.jpg",
                    title = "Valorant Masters Madrid",
                    subtitle = "FNATIC logra una victoria histórica en una final de infarto.",
                    modifier = Modifier.weight(1f)
                )
                SmallCard(
                    imageUrl = "https://cdn.cloudflare.steamstatic.com/steam/apps/990080/capsule_616x353.jpg",
                    title = "Hogwarts Legacy Update",
                    subtitle = "El parche mejora el rendimiento y agrega nuevos desafíos mágicos.",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 🔹 Menú inferior
        BottomMenu(selectedItem = "Trending")
    }
}

// 🔹 Tarjeta destacada
@Composable
fun HighlightCard(imageUrl: String, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B1B))
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000))))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(description, color = Color(0xFFB0B0B0), fontSize = 14.sp)
            }
        }
    }
}

// 🔹 Tarjeta secundaria (noticia pequeña)
@Composable
fun SmallCard(
    imageUrl: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier // ✅ recíbelo desde fuera
) {
    Card(
        modifier = modifier
            .height(180.dp), // ✅ solo la altura aquí
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B1B))
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(subtitle, color = Color(0xFFB0B0B0), fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrendingScreenPreview() {
    TrendingScreen()
}
