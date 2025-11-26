package com.example.videogamesapi.screens.viewmodel

import com.example.videogamesapi.data.MyGamesRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.videogamesapi.models.GamesDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class DetailViewModel (
    private val respository : MyGamesRepository = MyGamesRepository(),
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val gameId: Int = checkNotNull(savedStateHandle.get<Int>("gameId"))

    private val _detail = MutableStateFlow<GamesDetail?>(null)
    val detail: StateFlow<GamesDetail?> = _detail


}