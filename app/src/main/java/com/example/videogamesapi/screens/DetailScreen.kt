package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.videogamesapi.models.PlayerOnline
import com.example.videogamesapi.screens.viewmodel.DetailUiState
import com.example.videogamesapi.screens.viewmodel.DetailViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale.Companion.Crop

@Composable
fun DetailScreen(
    navController: NavController,
    gameId: String,
    viewModel: DetailViewModel = viewModel()
) {
    LaunchedEffect(gameId) {
        viewModel.load(gameId)
    }

    val state by viewModel.state.collectAsState()

    val primaryDarkColor = Color(0xFF1E0E4F)

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(primaryDarkColor), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

    if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize().background(primaryDarkColor), contentAlignment = Alignment.Center) {
            Text(text = state.error ?: "Detalle no disponible", color = Color.White)
        }
        return
    }

    DetailScreenContent(navController = navController, uiState = state)
}

@Composable
private fun DetailScreenContent(navController: NavController, uiState: DetailUiState) {
    val scrollState = rememberScrollState()
    val primaryDarkColor = Color(0xFF1E0E4F)

    Box(modifier = Modifier.fillMaxSize().background(primaryDarkColor)) {
        if (uiState.headerImageUrl.isNotBlank()) {
            AsyncImage(
                model = uiState.headerImageUrl,
                contentDescription = uiState.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
            )
        } else {
            Box(modifier = Modifier.fillMaxWidth().height(360.dp).background(Color.DarkGray)) {}
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, primaryDarkColor.copy(alpha = 0.95f)),
                        startY = 300f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(320.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (uiState.avatarImageUrl.isNotBlank()) {
                            AsyncImage(
                                model = uiState.avatarImageUrl,
                                contentDescription = "Avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(2.dp, Color.White, RoundedCornerShape(12.dp))
                            )
                        } else {
                            Box(modifier = Modifier.size(60.dp).clip(RoundedCornerShape(12.dp)).background(Color.Gray)) {}
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = uiState.title, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(text = uiState.category, color = Color.LightGray, fontSize = 14.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    StatItemDetail(label = "Rating", value = uiState.rating)
                    StatItemDetail(label = "Playtime", value = uiState.playTime)
                    StatItemDetail(label = "Players", value = uiState.playerCount)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = uiState.description, color = Color.LightGray)

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Game Images", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(uiState.screenshots) { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = "Screenshot",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(width = 180.dp, height = 100.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Players Online", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(uiState.playersOnline) { player: PlayerOnline ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (player.avatarUrl.isNotBlank()) {
                                AsyncImage(
                                    model = player.avatarUrl,
                                    contentDescription = player.username,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(50.dp))
                                )
                            } else {
                                Box(modifier = Modifier.size(50.dp).clip(RoundedCornerShape(50.dp)).background(Color.Gray)) {}
                            }
                            Text(text = player.username, color = Color.LightGray, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.width(50.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(top = 36.dp, start = 8.dp, end = 8.dp), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }
    }
}


@Composable
private fun StatItemDetail(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value.ifEmpty { "-" }, color = Color.White, fontWeight = FontWeight.Bold)
        Text(text = label, color = Color.LightGray, fontSize = 12.sp)
    }
}
