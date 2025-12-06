package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF2C2D51),
                        Color(0xFF4A5090)
                    )
                )
            )
    ) {

        // CABECERA (solo fondo)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        // CUERPO DEL FORMULARIO
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF373B5B),
                            Color(0xFF323253)
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp)
            ) {

                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 35.sp
                )

                Spacer(Modifier.height(50.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Email", color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email", tint = Color(0xFFB0B0B0))
                    }
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Password", color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password", tint = Color(0xFFB0B0B0))
                    }
                )

                Spacer(Modifier.height(25.dp))

                // 🔹 LOGIN
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onLoginSuccess() }
                ) {
                    Text("Login", fontSize = 15.sp)
                }

                Spacer(Modifier.height(20.dp))

                // 🔹 IR A REGISTER
                Text(
                    text = "Don't have an account? Sign up",
                    color = Color.White,
                    modifier = Modifier.clickable { onGoToRegister() }
                )
            }
        }
    }
}
