package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit,
    registeredEmail: String?,
    registeredPassword: String?
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val textFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,

        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,

        cursorColor = Color.White,

        focusedIndicatorColor = Color.White,
        unfocusedIndicatorColor = Color.LightGray,

        focusedLeadingIconColor = Color.White,
        unfocusedLeadingIconColor = Color.LightGray
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(listOf(Color(0xFF2C2D51), Color(0xFF4A5090)))
            )
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(300.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFF373B5B), Color(0xFF323253)))
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp)
            ) {

                Text("Login", color = Color.White, fontSize = 35.sp)

                Spacer(Modifier.height(50.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email", color = Color.White) },
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    colors = textFieldColors
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password", color = Color.White) },
                    leadingIcon = { Icon(Icons.Default.Lock, null) },
                    colors = textFieldColors
                )

                Spacer(Modifier.height(20.dp))

                if (error != null) {
                    Text(error!!, color = Color.Red)
                    Spacer(Modifier.height(10.dp))
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        when {
                            email.isBlank() || password.isBlank() ->
                                error = "Completa todos los campos"

                            registeredEmail == null ->
                                error = "Primero regístrate"

                            email != registeredEmail || password != registeredPassword ->
                                error = "Email o contraseña incorrectos"

                            else -> {
                                error = null
                                onLoginSuccess()
                            }
                        }
                    }
                ) {
                    Text("Login")
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Don't have an account? Sign up",
                    color = Color.White,
                    modifier = Modifier.clickable { onGoToRegister() }
                )
            }
        }
    }
}
