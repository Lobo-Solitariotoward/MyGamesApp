package com.example.videogamesapi.models

data class GamesDetail (
    val id: Int,
    val title: String,
    val category: String,
    val headerImageUrl: String,
    val avatarImageUrl: String,
    val description: String,
    val followers: String,
    val players: String,
    val streamers: String,
    val liveStreams: List<LiveStream>,
    val playersOnline: List<PlayerOnline>
)

data class LiveStream(
    val title: String,
    val user: String,
    val viewerCount: String,
    val streamImageUrl: String
)

data class PlayerOnline(
    val username: String,
    val avatarUrl: String
)