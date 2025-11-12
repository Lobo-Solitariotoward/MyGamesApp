package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.videogamesapi.screens.BottomBar


// Paleta tomada de colores

private val BgColor = Color(0xFF0C0F27)
private val Accent = Color(0xFF7A6BFF)
private val OnBg = Color.White
private val Muted = Color(0xFFB0B0B0)
private val BottomBar = Color(0xFF1E1B1B)


// Datos de ejemplo

data class Game(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String
)

private fun posterUrl(seed: String) = "https://picsum.photos/seed/$seed/600/900"
private fun bannerUrl(seed: String) = "https://picsum.photos/seed/$seed/1280/720"

private val topBanners = listOf(
    Game("op", "Elden Ring", "RPG • Souls-like", bannerUrl("eldenring")),
    Game("ds", "Starfield", "RPG • Sci-Fi", bannerUrl("starfield")),
    Game("cv", "Hades II", "Rogue-like", bannerUrl("hades2"))
)

private val newReleases = listOf(
    Game("g1", "Let This Grieving Soul Retry", "Hace 2 horas • Subtítulos", posterUrl("retry1")),
    Game("g2", "The Banished Court Magician", "Hace 2 horas • Dob/ Sub", posterUrl("banished1")),
    Game("g3", "A Wild Last Boss Appeared", "Hace 2 horas • Dob Japonés", posterUrl("boss1")),
    Game("g4", "Chronicles of Aether", "Hace 3 horas • Subtítulos", posterUrl("aether1"))
)

private val action = listOf(
    Game("a1", "Scarlet Blade", "Acción • Hack & Slash", posterUrl("scarlet1")),
    Game("a2", "Azure Saga", "Acción • RPG", posterUrl("azure1")),
    Game("a3", "Iron Vanguard", "Acción • Shooter", posterUrl("iron1")),
    Game("a4", "Dragon Runes", "Acción • Aventura", posterUrl("dragon1"))
)

// UI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    genreTitle: String,
    onBack: () -> Unit,
    onSearch: () -> Unit,
    onCast: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Atrás", tint = OnBg)
            }
        },
        title = {
            Column {
                Text("Géneros", fontSize = 12.sp, color = Muted)
                Text(genreTitle, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = OnBg)
            }
        },
        actions = {
            IconButton(onClick = onSearch) { Icon(Icons.Rounded.Search, contentDescription = "Buscar", tint = OnBg) }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BgColor,
            navigationIconContentColor = OnBg,
            titleContentColor = OnBg,
            actionIconContentColor = OnBg
        )
    )
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = OnBg)
        Spacer(Modifier.weight(1f))
        if (actionText != null && onAction != null) {
            TextButton(
                onClick = onAction,
                colors = ButtonDefaults.textButtonColors(contentColor = Accent)
            ) { Text(actionText, fontWeight = FontWeight.Medium) }
        }
    }
}

@Composable
private fun BannerRow(items: List<Game>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { game ->
            BannerCard(game)
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun BannerCard(game: Game) {
    val shape = MaterialTheme.shapes.medium
    Column(
        modifier = Modifier
            .width(280.dp)
            .clip(shape)
    ) {
        AsyncImage(
            model = game.imageUrl,
            contentDescription = game.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(shape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = game.title,
            modifier = Modifier.padding(horizontal = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            color = OnBg
        )
        Text(
            text = game.subtitle,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Muted,
            fontSize = 12.sp
        )
        Spacer(Modifier.height(6.dp))
    }
}

@Composable
private fun PosterRow(items: List<Game>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(items) { game -> PosterCard(game) }
    }
}

@Composable
private fun PosterCard(game: Game) {
    Column(
        modifier = Modifier.width(160.dp)
    ) {
        val shape = MaterialTheme.shapes.medium
        AsyncImage(
            model = game.imageUrl,
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .clip(shape)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = game.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            color = OnBg
        )
        Text(
            text = game.subtitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Muted,
            fontSize = 12.sp
        )
    }
}