package com.example.videogamesapi.screens.viewmodel

//import com.example.videogamesapi.data.MyGamesRepository
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.videogamesapi.models.GamesDetail
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//
//class DetailViewModel (
//    private val respository : MyGamesRepository = MyGamesRepository(),
//    savedStateHandle: SavedStateHandle
//) : ViewModel() {
//    private val gameId: Int = checkNotNull(savedStateHandle.get<Int>("gameId"))
//
//    private val _detail = MutableStateFlow<GamesDetail?>(null)
//    open val detail: StateFlow<GamesDetail?> = _detail
//
//    private val _isLoading = MutableStateFlow(true)
//    val isLoading: StateFlow<Boolean> = _isLoading
//
//    init {
//        // Carga los datos apenas se crea el ViewModel
//        loadGameDetail()
//    }
//
//    private fun loadGameDetail() {
//        viewModelScope.launch {
//            try {
//                _isLoading.value = true
//
//                // Obtenemos los datos del repositorio usando el gameId
//                val detailData = respository.getGameDetail(gameId)
//
//                _detail.value = detailData
//            } catch (e: Exception) {
//                println("Error loading game detail for ID $gameId: ${e.message}")
//                _detail.value = null
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}