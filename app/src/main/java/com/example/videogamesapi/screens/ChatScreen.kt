package com.example.videogamesapi.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
    var message by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Queen", "More fun than I think any of us have had in a while.", false,
                "https://i.pinimg.com/originals/5e/ca/27/5eca2762d126defa7680aff778bf1df2.jpg", true),
            ChatMessage("UrNext", "Do you love me and how much?", true,
                "https://i.pinimg.com/1200x/ee/20/b6/ee20b6fbb928e402b79601325dfcc6e9.jpg", true),
            ChatMessage("Blank0", "HELLO YOUTUBE!!!!!!!!!! 😂", false,
                "https://i.pinimg.com/originals/6f/77/45/6f7745a82ed8e158dc17e9253674180f.jpg", false),
            ChatMessage("RaptorJesvs", "If I could go negative, I would. 😆", true,
                "https://i.pinimg.com/736x/bb/d5/15/bbd51518913ec655716400d7e34b6a6a.jpg", true),
            ChatMessage("winterhaven", "Depends on your rates. 😏", false,
                "https://i.pinimg.com/originals/20/9f/b1/209fb11c88ca45d6e3f6a6a8c5494b23.jpg", true),
            ChatMessage("Nova", "Can’t believe we’re still awake 😂", false,
                "https://i.pinimg.com/originals/0d/2c/1d/0d2c1d49a44ab8aed3ebd6660402d5f2.jpg", true),
            ChatMessage("UrNext", "We gamers never sleep 😎", true,
                "https://i.pinimg.com/736x/90/f9/81/90f981806d0a349998e8eb4521ebf4a8.jpg", true),
            ChatMessage("Blank0", "Stream’s starting soon!", false,
                "https://i.pinimg.com/474x/04/69/45/04694507556179005024817ad3d40632.jpg", false),
            ChatMessage("Queen", "Let’s gooo 🔥🔥🔥", false,
                "https://i.pinimg.com/736x/d4/d1/41/d4d141e8bf4e8c2a773785b35ca1f8c1.jpg", true),
            ChatMessage("RaptorJesvs", "GG everyone! 🎮", true,
                "https://fiverr-res.cloudinary.com/images/t_main1,q_auto,f_auto,q_auto,f_auto/gigs/392177261/original/dc71edee38b6a6ae07992b6bc1d01e25def7b7a3/draw-pfp-avatar-icon-album-cover-portrait-of-your-oc-vtuber-anime-character.png", true)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box {
            // Fondo con imagen
            Image(
                painter = rememberAsyncImagePainter(
                    "https://www.xtrafondos.com/wallpapers/vertical/chica-anime-en-paisaje-3730.jpg"
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Gradiente oscuro inferior
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0x66000000), Color(0xCC000000))
                        )
                    )
            )

            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Text(
                    text = "Community Chat 💬",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Lista de mensajes
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    messages.forEach { msg ->
                        AnimatedVisibility(visible = true) {
                            MessageBubble(msg)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubble(msg: ChatMessage) {
    val bubbleColor = if (msg.isMine)
        Brush.verticalGradient(listOf(Color(0xFF7A6BFF), Color(0xFF5A50E8)))
    else
        Brush.verticalGradient(listOf(Color(0xFF3A3A3A), Color(0xFF2B2B2B)))

    val alignment = if (msg.isMine) Alignment.End else Alignment.Start

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!msg.isMine) UserAvatar(msg)

            Column(horizontalAlignment = alignment) {
                Text(
                    text = msg.username,
                    color = Color(0xFFB0B0B0),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(bubbleColor)
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(msg.text, color = Color.White, fontSize = 15.sp)
                }
            }

            if (msg.isMine) UserAvatar(msg)
        }
    }
}

@Composable
fun UserAvatar(msg: ChatMessage) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(msg.profileUrl),
            contentDescription = msg.username,
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
        )
        if (msg.isOnline) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color(0xFF4CAF50), CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen()
}
