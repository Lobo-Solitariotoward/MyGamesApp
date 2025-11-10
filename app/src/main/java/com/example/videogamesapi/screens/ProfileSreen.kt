package com.example.videogamesapi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import kotlin.math.max

// Estructura principal con menú inferior
@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0F27))
    ) {
        Box(modifier = Modifier.weight(1f)) {
            ProfileContent()
        }
        BottomMenu(selectedItem = "Profile")
    }
}

// 🔹 Contenido del perfil
@Composable
fun ProfileContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0F27))
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                "https://img.goodfon.com/original/1920x1080/8/47/valorant-valorant-video-igry-takticheskii-shuter-riot-game-2.jpg"
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Notifications, contentDescription = "Notificaciones", tint = Color.White)
            Icon(Icons.Default.MoreVert, contentDescription = "Menú", tint = Color.White)
        }

        // Fondo degradado inferior
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 200.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                    .drawBehind {
                        val w = size.width
                        val h = size.height
                        val red = Color(0xFFA80C0D)

                        drawRect(color = Color(0xFF443E3E))
                        val radius = max(w, h)
                        val glowRadius = radius / 1.5f
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(red.copy(alpha = 0.45f), Color.Transparent),
                                center = Offset(0f, h),
                                radius = glowRadius
                            )
                        )
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(red.copy(alpha = 0.45f), Color.Transparent),
                                center = Offset(w, h),
                                radius = glowRadius
                            )
                        )
                    }
            )

            // Contenido desplazable
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-65).dp)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        "https://i.pinimg.com/736x/e1/c6/9a/e1c69a374f0888d55f1e307456c46ace.jpg"
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .shadow(12.dp, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(8.dp))
                Text("@cat11", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("New York, USA", color = Color(0xFFB0B0B0), fontSize = 13.sp)
                Spacer(Modifier.height(16.dp))

                // Chips
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GameChip("Valorant", Color(0xFFA80C0D))
                    GameChip("Call Of Duty", Color(0xFF4A90E2))
                    GameChip("Apex", Color(0xFFE5C100), Color(0xFF1C1C1C))
                }

                Spacer(Modifier.height(24.dp))

                // Estadísticas
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x331C1C1C)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatItem("Posts", "1K")
                        VerticalDivider(Modifier.height(35.dp).width(1.dp), color = Color(0x66FFFFFF))
                        StatItem("Followers", "420K")
                        VerticalDivider(Modifier.height(35.dp).width(1.dp), color = Color(0x66FFFFFF))
                        StatItem("Following", "95")
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Streams
                SectionTitle("Recent Streams")
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StreamCard("Valorant Ranked With New Tenz Mouse :)", "https://mir-s3-cdn-cf.behance.net/project_modules/1400/ff3883118217021.6084f69f22ab5.png")
                    StreamCard("Let's Hangout — APEX Warm-up", "https://www.zerging.net/wp-content/uploads/2019/05/Zerging-Overlay-Apex-Legends-Preview.jpg")
                    StreamCard("Call of Duty: Ranked Highlights", "https://cdn.mos.cms.futurecdn.net/DY24625s6z9gdNu7DEdr8H.jpg")
                }

                Spacer(Modifier.height(30.dp))

                // Amigos conectados
                SectionTitle("Connected Friends")
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FriendCard("Jett", "https://i.ytimg.com/vi/zqkJ30O3CjU/maxresdefault.jpg", "https://wallpapers.com/images/hd/gaming-profile-pictures-p2viuapqmpk5gvv5.jpg")
                    FriendCard("Sage", "https://images.squarespace-cdn.com/content/v1/60c7ba0eb2862230f13fb3e1/39030884-1f6d-4d03-80f9-a2f07d9b86ef/Key+Art+2.png", "https://i.etsystatic.com/41321847/r/il/b4803d/7025708203/il_fullxfull.7025708203_bu6p.jpg")
                    FriendCard("Phoenix", "https://s1.pearlcdn.com/NAEU/Upload/thumbnail/2024/9IP46AAV0G16I0LF20240605092513052.923x522.jpg", "https://i.pravatar.cc/150?img=68")
                }

                Spacer(Modifier.height(30.dp))

                // Recompensas o Logros
                SectionTitle("Rewards & Achievements")
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 12.dp, bottom = 80.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RewardCard("Top 1 Valorant", "https://www.zleague.gg/theportal/wp-content/uploads/2023/04/image-19.png")
                    RewardCard("Pro Player Badge", "https://pbs.twimg.com/media/G0JdQp3XsAAwCY5.jpg")
                    RewardCard("50K Followers", "https://cdn-icons-png.flaticon.com/512/4712/4712136.png")
                }
                Spacer(Modifier.height(42.dp))
            }
        }
    }
}

// RewardCard Composable
@Composable
fun RewardCard(title: String, imageUrl: String) {
    Box(
        modifier = Modifier
            .width(210.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2C2C2C))
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = title,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color(0x55000000))
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        )
    }
}

// Barra inferior
@Composable
fun BottomMenu(selectedItem: String = "Profile") {
    val items = listOf("Home", "Search", "Trending", "Chat", "Profile")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Videocam,
        Icons.Default.ChatBubbleOutline,
        Icons.Default.Person
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E1B1B),
                        Color(0xFF2D2929)
                    )
                )
            )
            .padding(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = item == selectedItem
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        tint = if (isSelected) Color(0xFF7A6BFF) else Color(0xFFB8B8B8),
                        modifier = Modifier.size(if (isSelected) 28.dp else 24.dp)
                    )
                    if (isSelected) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .height(6.dp)
                                .background(Color(0xFF7A6BFF), CircleShape)
                        )
                    }
                }
            }
        }
    }
}

// Reutilizables
@Composable
fun GameChip(text: String, background: Color, textColor: Color = Color.White) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(background.copy(alpha = 0.85f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text, color = textColor, fontWeight = FontWeight.Medium, fontSize = 12.sp)
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(label, color = Color(0xFFB0B0B0), fontSize = 12.sp)
    }
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp, bottom = 10.dp)
        )
    }
}

@Composable
fun StreamCard(title: String, imageUrl: String) {
    Box(
        modifier = Modifier
            .width(210.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(55.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xAA000000))
                    )
                )
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun FriendCard(username: String, gameBg: String, profilePic: String) {
    Box(
        modifier = Modifier
            .width(210.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2C2C2C))
    ) {
        Image(
            painter = rememberAsyncImagePainter(gameBg),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color(0x55000000))
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = rememberAsyncImagePainter(profilePic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .shadow(5.dp, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color(0xFF4CAF50), CircleShape)
                        .align(Alignment.BottomEnd)
                        .offset(x = 3.dp, y = 3.dp)
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(username, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text("Online", color = Color(0xFF8BC34A), fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader()
}
