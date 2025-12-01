package com.example.videogamesapi.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videogamesapi.data.MyGamesRepository
import com.example.videogamesapi.models.Games
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MyGamesRepository = MyGamesRepository()
) : ViewModel() {

    // Lista de juegos
    private val _games = MutableStateFlow<List<Games>>(emptyList())
    val games: StateFlow<List<Games>> = _games

    // Juego seleccionado
    private val _selectedGame = MutableStateFlow<Games?>(null)
    val selectedGame: StateFlow<Games?> = _selectedGame

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Mensaje de error (si ocurre algo en la llamada a la API)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        // caragar todos los juegos al iniciar
        loadAllGames()
    }

    /**
     * Carga juegos desde la API
     */
    fun loadAllGames() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getAllGames()
                _games.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar juegos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Busca juegos por término (/games/local/search/{term})
     */
    fun searchGames(term: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = if (term.isBlank()) {
                    repository.getAllGames()
                } else {
                    repository.searchGames(term)
                }
                _games.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Error en la búsqueda"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Carga un juego por id (/games/local/id/{id})
     * útil para DetailScreen
     */
    fun loadGameById(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val game = repository.getGameById(id)
                _selectedGame.value = game
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar detalle"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Limpia el juego seleccionado al salir de detail
     */
    fun clearSelectedGame() {
        _selectedGame.value = null
    }
}
