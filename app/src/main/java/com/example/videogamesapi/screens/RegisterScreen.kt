package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF2C2D51),
                        Color(0xFF4A5090)
                    )
                )
            )

    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ){

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 210.dp)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF373B5B),
                            Color(0xFF323253)
                        )
                    )
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 50.dp)

            ) {
                Text(text = "Sign Up",
                    color = Color.White,
                    fontSize = 35.sp

                )
                Spacer(modifier = Modifier.height(50.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Username", color = Color.White)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Email",
                            tint = Color(0xFFB0B0B0)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(25.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Email", color = Color.White)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email,
                            contentDescription = "Lock",
                            tint = Color(0xFFB0B0B0)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(25.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Password", color = Color.White)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock,
                            contentDescription = "Lock",
                            tint = Color(0xFFB0B0B0)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(25.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = " Confirm password", color = Color.White)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock,
                            contentDescription = "Lock",
                            tint = Color(0xFFB0B0B0)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { }
                ) {
                    Text(
                        text = "Sign Up", fontSize = 15.sp
                    )
                }


            }

        }

    }

}

@Preview(showBackground = true,
    showSystemUi = true)

@Composable
fun RegisterScreenPreview(){
    RegisterScreen()
}


