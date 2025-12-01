package com.example.videogamesapi.screens


//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Airplay
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.MoreVert
//import androidx.compose.material.icons.filled.People
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material.icons.filled.Timer
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.SegmentedButtonDefaults.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil3.compose.AsyncImage
//import com.example.videogamesapi.models.GamesDetail
//import com.example.videogamesapi.models.PlayerOnline
//import com.example.videogamesapi.screens.viewmodel.DetailViewModel
//
//
//@Composable
//fun DetailScreen(
//    viewModel: DetailViewModel = viewModel(),
//    onBackClicked: () -> Unit
//) {
//    val detailState by viewModel.detail.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//
//    // Pasa datos reales o un fallback al contenido puro
//    val detail = detailState
//    if (detail == null) {
//        // Si aún no hay detalle muestra carga vía la UI pura (o un placeholder)
//        DetailScreenContent(detail = sampleGamesDetail(), isLoading = isLoading, onBackClicked = onBackClicked)
//    } else {
//        DetailScreenContent(detail = detail, isLoading = isLoading, onBackClicked = onBackClicked)
//    }
//}
//@Composable
//fun DetailScreenContent(
//    detail: GamesDetail,
//    isLoading: Boolean,
//    onBackClicked: () -> Unit
//) {
//    val scrollState = rememberScrollState()
//    val primaryDarkColor = Color(0xFF1E0E4F)
//    val followButtonColor = Color(0xFF673AB7)
//
//    if (isLoading) {
//        Box(modifier = Modifier.fillMaxSize().background(primaryDarkColor), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator(color = Color.White)
//        }
//        return
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(primaryDarkColor)
//    ) {
//        AsyncImage(
//            model = detail.headerImageUrl,
//            contentDescription = detail.title,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp)
//        )
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp)
//                .background(
//                    Brush.verticalGradient(
//                        colors = listOf(Color.Transparent, primaryDarkColor.copy(alpha = 0.95f)),
//                        startY = 500f
//                    )
//                )
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(scrollState)
//        ) {
//            Spacer(modifier = Modifier.height(350.dp))
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp)
//            ) {
//                TitleAndFollowSection(detail = detail, buttonColor = followButtonColor)
//                Spacer(modifier = Modifier.height(20.dp))
//                StatsSection(detail = detail)
//                Spacer(modifier = Modifier.height(20.dp))
//                Text(text = detail.description, color = Color.LightGray, fontSize = 14.sp)
//                Spacer(modifier = Modifier.height(30.dp))
//                SectionHeaderDetail(title = "Game Images")
//                ScreenshotsList(screenshots = detail.screenshots)
//                Spacer(modifier = Modifier.height(30.dp))
//                SectionHeaderDetail(title = "Players Online")
//                PlayersOnlineList(players = detail.playersOnline)
//                Spacer(modifier = Modifier.height(80.dp))
//            }
//        }
//
//        TopBarDetail(onBackClicked = onBackClicked)
//    }
//}
//
//
//@Composable
//fun TopBarDetail(onBackClicked: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        IconButton(onClick = onBackClicked) {
//            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
//        }
//        IconButton(onClick = { /* Menú de opciones */ }) {
//            Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
//        }
//    }
//}
//
//@Composable
//fun TitleAndFollowSection(detail: GamesDetail, buttonColor: Color) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.Bottom
//    ) {
//        Column(modifier = Modifier.weight(1f)) {
//            // Avatar del juego (usa AsyncImage para URL real)
//            AsyncImage(
//                model = detail.avatarImageUrl,
//                contentDescription = "Avatar",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(60.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .border(2.dp, Color.White, RoundedCornerShape(12.dp))
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = detail.title,
//                color = Color.White,
//                fontSize = 32.sp,
//                fontWeight = FontWeight.Black
//            )
//            Text(
//                text = detail.category,
//                color = Color.LightGray.copy(alpha = 0.7f),
//                fontSize = 16.sp
//            )
//        }
//
//        Button(
//            onClick = { /* Acción de seguir */ },
//            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
//            shape = RoundedCornerShape(16.dp),
//            modifier = Modifier.align(Alignment.CenterVertically)
//        ) {
//            Text("Follow", fontWeight = FontWeight.SemiBold)
//        }
//    }
//}
//
//@Composable
//fun StatsSection(detail: GamesDetail) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        StatItem(icon = Icons.Default.Star, value = detail.rating, label = "Rating")
//        StatItem(icon = Icons.Default.Timer, value = detail.playTime, label = "Playtime")
//        StatItem(icon = Icons.Default.People, value = detail.playerCount, label = "Players")
//    }
//}
//
//@Composable
//fun StatItem(icon: ImageVector, value: String, label: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Icon(icon, contentDescription = label, tint = Color.LightGray, modifier = Modifier.size(24.dp))
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
//        Text(text = label, color = Color.LightGray, fontSize = 12.sp)
//    }
//}
//
//@Composable
//fun SectionHeaderDetail(title: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
//        Text(text = "See all", color = Color.LightGray.copy(alpha = 0.7f), fontSize = 14.sp, modifier = Modifier.clickable { /* Ver todo */ })
//    }
//}
//
//@Composable
//fun ScreenshotsList(screenshots: List<String>) {
//    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
//        items(screenshots) { url ->
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                modifier = Modifier.size(width = 180.dp, height = 100.dp)
//            ) {
//                AsyncImage(
//                    model = url,
//                    contentDescription = "Screenshot",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun PlayersOnlineList(players: List<PlayerOnline>) {
//    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//        items(players) { player ->
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                AsyncImage(
//                    model = player.avatarUrl,
//                    contentDescription = player.username,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape)
//                        .border(2.dp, Color.White, CircleShape)
//                )
//                Text(
//                    text = player.username,
//                    color = Color.LightGray,
//                    fontSize = 10.sp,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    modifier = Modifier.width(50.dp)
//                )
//            }
//        }
//    }
//}
//private fun sampleGamesDetail(): GamesDetail {
//    return GamesDetail(
//        id = 0,
//        title = "Sample Game",
//        headerImageUrl = "https://shared.fastly.steamstatic.com/store_item_assets/steam/apps/1778820/capsule_616x353.jpg?t=1760396803",
//        avatarImageUrl = "https://i.pinimg.com/736x/45/b4/57/45b457f727a893cb847f14a2d6fb01db.jpg",
//        category = "Action",
//        description = "Descripción de ejemplo para el preview.",
//        screenshots = listOf(
//            "https://cdn.shopify.com/s/files/1/0075/2425/3809/files/TEKKEN-8-EasySMX-game-controller-1.jpg?v=1698744149",
//            "https://bgeek.eu/wp-content/uploads/2024/02/F5Safq_WkAAzdTE-scaled.jpg",
//            "https://sm.ign.com/ign_latam/gallery/t/tekken-8-r/tekken-8-reina-screenshots_3qn5.jpg"
//        ),
//        rating = "4.5",
//        playTime = "12h",
//        playerCount = "1.2K",
//        playersOnline = listOf(
//            PlayerOnline(avatarUrl = "https://avatarfiles.alphacoders.com/334/thumb-1920-334768.jpg", username = "Alice"),
//            PlayerOnline(avatarUrl = "https://wallpapers.com/images/hd/aesthetic-anime-boy-icon-shirou-emiya-iz70r0ohjlfutvf4.jpg", username = "Bob")
//        )
//    )
//}
////
