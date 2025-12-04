package com.example.videogamesapi.data

import com.example.videogamesapi.models.Games
import com.example.videogamesapi.remote.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyGamesRepository(
    private val api: com.example.videogamesapi.remote.GamesApi = NetworkModule.gamesApi
) {

    suspend fun getAllGames(): List<Games> = withContext(Dispatchers.IO) {
        api.getAllGames()
    }

    suspend fun getGameById(id: String): Games = withContext(Dispatchers.IO) {
        api.getGameById(id)
    }

    suspend fun searchGames(term: String): List<Games> = withContext(Dispatchers.IO) {
        api.searchGames(term)
    }
}
