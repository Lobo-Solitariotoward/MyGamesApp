package com.example.videogamesapi.remote

import com.example.videogamesapi.models.Games
import retrofit2.http.GET
import retrofit2.http.Path

interface GamesApi {

    @GET("games/local")
    suspend fun getAllGames(): List<Games>

    @GET("games/local/id/{id}")
    suspend fun getGameById(@Path("id") id: String): Games

    @GET("games/local/search/{term}")
    suspend fun searchGames(@Path("term") term: String): List<Games>
}
