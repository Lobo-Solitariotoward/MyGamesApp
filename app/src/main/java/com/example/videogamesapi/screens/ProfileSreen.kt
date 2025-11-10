package com.example.videogamesapi.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import kotlin.math.max

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0F27))
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen superior
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

            // Iconos superiores
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones",
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menú",
                    tint = Color.White
                )
            }

            // Fondo inferior con degradado y panel redondeado
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 200.dp)
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
            ) {
                // Contenido del perfil
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen de perfil
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
                    Text(
                        text = "@cat11",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "New York, USA",
                        color = Color(0xFFB0B0B0),
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(16.dp))

                    // Chips de juegos
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        GameChip("Valorant", Color(0xFFA80C0D))
                        GameChip("Call Of Duty", Color(0xFF4A90E2))
                        GameChip("Apex", Color(0xFFE5C100), Color(0xFF1C1C1C))
                    }

                    Spacer(Modifier.height(24.dp))

                    // Tarjeta de estadísticas
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
                            VerticalDivider(
                                modifier = Modifier.height(35.dp).width(1.dp),
                                color = Color(0x66FFFFFF)
                            )
                            StatItem("Followers", "420K")
                            VerticalDivider(
                                modifier = Modifier.height(35.dp).width(1.dp),
                                color = Color(0x66FFFFFF)
                            )
                            StatItem("Following", "95")
                        }
                    }
                }
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader()
}
