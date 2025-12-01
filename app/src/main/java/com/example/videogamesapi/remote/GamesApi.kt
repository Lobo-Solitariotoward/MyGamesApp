package com.example.videogamesapi.remote

import com.example.videogamesapi.models.Games
import retrofit2.http.GET
import retrofit2.http.Path

interface GamesApi {

    @GET("api/games/local")
    suspend fun getAllGames(): List<Games>

    @GET("api/games/local/id/{id}")
    suspend fun getGameById(@Path("id") id: String): Games

    @GET("api/games/local/search/{term}")
    suspend fun searchGames(@Path("term") term: String): List<Games>
}

