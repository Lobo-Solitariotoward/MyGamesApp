package com.example.videogamesapi.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videogamesapi.data.MyGamesRepository
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.models.PlayerOnline
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailUiState(
    val id: String = "",
    val title: String = "Unknown",
    val headerImageUrl: String = "",
    val avatarImageUrl: String = "",
    val category: String = "",
    val description: String = "",
    val screenshots: List<String> = emptyList(),
    val rating: String = "",
    val playTime: String = "",
    val playerCount: String = "",
    val playersOnline: List<PlayerOnline> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class DetailViewModel(
    private val repository: MyGamesRepository = MyGamesRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState(isLoading = true))
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    fun load(gameId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val game: Games = repository.getGameById(gameId)
                val mapped = mapGameToUi(game, gameId)
                _state.value = mapped.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = DetailUiState(isLoading = false, error = e.message ?: "Error desconocido", id = gameId)
            }
        }
    }

    private fun mapGameToUi(game: Games, fallbackId: String): DetailUiState {
        val id = tryInvokeStringOrCollection(game, listOf("id", "getId")) ?: fallbackId
        val title = tryInvokeStringOrCollection(game, listOf("title", "getTitle", "name", "getName", "nombre")) ?: id
        val header = tryInvokeStringOrCollection(game, listOf("headerImageUrl", "headerImage", "imageUrl", "image", "cover", "thumbnail", "url")) ?: ""
        val avatar = tryInvokeStringOrCollection(game, listOf("avatarImageUrl", "avatar", "icon", "logo")) ?: ""
        val category = tryInvokeStringOrCollection(game, listOf("category", "getCategory", "genres", "tags")) ?: ""
        val description = tryInvokeStringOrCollection(game, listOf("description", "desc", "overview", "summary")) ?: ""
        val rating = tryInvokeStringOrCollection(game, listOf("rating", "score")) ?: ""
        val playTime = tryInvokeStringOrCollection(game, listOf("playTime", "play_time", "hours")) ?: ""
        val playerCount = tryInvokeStringOrCollection(game, listOf("playerCount", "players", "player_count")) ?: ""

        val screenshots = tryInvokeListOfString(game, listOf("screenshots", "images", "gallery")) ?: emptyList()

        val playersOnline = tryInvokePlayers(game, listOf("playersOnline", "players_online", "onlinePlayers", "users")) ?: emptyList()

        return DetailUiState(
            id = id,
            title = title,
            headerImageUrl = header,
            avatarImageUrl = avatar,
            category = category,
            description = description,
            screenshots = screenshots,
            rating = rating,
            playTime = playTime,
            playerCount = playerCount,
            playersOnline = playersOnline
        )
    }

    // intenta obtener String o Collection/Array y concatenar
    private fun tryInvokeStringOrCollection(target: Any, names: List<String>): String? {
        val clazz = target::class.java
        for (n in names) {
            try {
                val field = clazz.declaredFields.firstOrNull { it.name.equals(n, ignoreCase = true) }
                if (field != null) {
                    field.isAccessible = true
                    val value = field.get(target) ?: continue
                    return when (value) {
                        is String -> value
                        is Collection<*> -> value.joinToString(", ") { it.toString() }
                        is Array<*> -> value.joinToString(", ") { it.toString() }
                        else -> value.toString()
                    }
                }
                val method = clazz.methods.firstOrNull { it.name.equals(n, ignoreCase = true) && it.parameterCount == 0 }
                if (method != null) {
                    val res = method.invoke(target) ?: continue
                    return when (res) {
                        is String -> res
                        is Collection<*> -> res.joinToString(", ") { it.toString() }
                        is Array<*> -> res.joinToString(", ") { it.toString() }
                        else -> res.toString()
                    }
                }
            } catch (_: Exception) { /* ignore */ }
        }
        return null
    }

    // intenta obtener lista de strings (screenshots, images ...)
    private fun tryInvokeListOfString(target: Any, names: List<String>): List<String>? {
        val clazz = target::class.java
        for (n in names) {
            try {
                val field = clazz.declaredFields.firstOrNull { it.name.equals(n, ignoreCase = true) }
                if (field != null) {
                    field.isAccessible = true
                    val value = field.get(target) ?: continue
                    return when (value) {
                        is Collection<*> -> value.map { it.toString() }
                        is Array<*> -> value.map { it.toString() }
                        is String -> listOf(value)
                        else -> null
                    }
                }
                val method = clazz.methods.firstOrNull { it.name.equals(n, ignoreCase = true) && it.parameterCount == 0 }
                if (method != null) {
                    val res = method.invoke(target) ?: continue
                    return when (res) {
                        is Collection<*> -> res.map { it.toString() }
                        is Array<*> -> res.map { it.toString() }
                        is String -> listOf(res)
                        else -> null
                    }
                }
            } catch (_: Exception) { /* ignore */ }
        }
        return null
    }

    // intenta mapear una colección de objetos a PlayerOnline si es posible, o crear placeholders simples
    private fun tryInvokePlayers(target: Any, names: List<String>): List<PlayerOnline>? {
        val clazz = target::class.java
        for (n in names) {
            try {
                val field = clazz.declaredFields.firstOrNull { it.name.equals(n, ignoreCase = true) }
                if (field != null) {
                    field.isAccessible = true
                    val value = field.get(target) ?: continue
                    return when (value) {
                        is Collection<*> -> value.mapNotNull { toPlayerOnline(it) }
                        is Array<*> -> value.mapNotNull { toPlayerOnline(it) }
                        else -> null
                    }
                }
                val method = clazz.methods.firstOrNull { it.name.equals(n, ignoreCase = true) && it.parameterCount == 0 }
                if (method != null) {
                    val res = method.invoke(target) ?: continue
                    return when (res) {
                        is Collection<*> -> res.mapNotNull { toPlayerOnline(it) }
                        is Array<*> -> res.mapNotNull { toPlayerOnline(it) }
                        else -> null
                    }
                }
            } catch (_: Exception) { /* ignore */ }
        }
        return null
    }

    private fun toPlayerOnline(obj: Any?): PlayerOnline? {
        if (obj == null) return null
        // si ya es PlayerOnline
        if (obj is PlayerOnline) return obj
        // intentar extraer properties por reflexión
        val clazz = obj::class.java
        val avatar = try {
            val f = clazz.declaredFields.firstOrNull { it.name.equals("avatarUrl", ignoreCase = true) || it.name.equals("avatar", ignoreCase = true) || it.name.equals("image", ignoreCase = true) }
            f?.apply { isAccessible = true }?.get(obj)?.toString()
        } catch (_: Exception) { null }
        val username = try {
            val f = clazz.declaredFields.firstOrNull { it.name.equals("username", ignoreCase = true) || it.name.equals("name", ignoreCase = true) || it.name.equals("user", ignoreCase = true) }
            f?.apply { isAccessible = true }?.get(obj)?.toString()
        } catch (_: Exception) { null }
        return PlayerOnline(avatarUrl = avatar ?: "", username = username ?: "user")
    }
}
