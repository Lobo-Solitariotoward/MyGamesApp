package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
fun RegisterScreen(
    onRegisterSuccess: () -> Unit
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

        // Fondo de la parte superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        )

        // Contenedor del formulario
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 210.dp)
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
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 35.sp
                )

                Spacer(modifier = Modifier.height(50.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Username", color = Color.White) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Username",
                            tint = Color(0xFFB0B0B0)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Email", color = Color.White) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            tint = Color(0xFFB0B0B0)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Password", color = Color.White) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password",
                            tint = Color(0xFFB0B0B0)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Confirm Password", color = Color.White) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Confirm Password",
                            tint = Color(0xFFB0B0B0)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                // 🔹 BOTÓN DE REGISTRO
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onRegisterSuccess() }
                ) {
                    Text("Sign Up", fontSize = 15.sp)
                }
            }
        }
    }
}