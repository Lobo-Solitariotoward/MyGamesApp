package com.example.videogamesapi.screens.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videogamesapi.data.MyGamesRepository
import com.example.videogamesapi.models.Games
import kotlinx.coroutines.launch

data class MyGamesUiState(
    val isLoading: Boolean = false,
    val games: List<Games> = emptyList(),
    val errorMessage: String? = null
)

class MyGamesViewModel(
    private val repository: MyGamesRepository = MyGamesRepository()
) : ViewModel() {

    // AHORA ES observable para Compose
    var uiState by mutableStateOf(MyGamesUiState(isLoading = true))
        private set

    init {
        loadGames()
    }

    fun loadGames() {
        uiState = uiState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val games = repository.getAllGames()
                uiState = uiState.copy(
                    isLoading = false,
                    games = games,
                    errorMessage = null
                )
            } catch (e: Exception) {
                Log.e("MyGamesViewModel", "Error cargando juegos", e)
                uiState = uiState.copy(
                    isLoading = false,
                    games = emptyList(),
                    errorMessage = e.message ?: "Error desconocido"
                )
            }
        }
    }
}
