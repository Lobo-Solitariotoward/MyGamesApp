package com.example.videogamesapi.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0F27))
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader()
}
