package com.example.videogamesapi.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videogamesapi.data.MyGamesRepository
import com.example.videogamesapi.models.Category
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.models.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MyGamesRepository = MyGamesRepository()
) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _trendingGames = MutableStateFlow<List<Games>>(emptyList())
    val trendingGames: MutableStateFlow<List<Games>> = _trendingGames

    private val _newGames = MutableStateFlow<List<Games>>(emptyList())
    val newGames: MutableStateFlow<List<Games>> = _newGames

    private val _recommendedGames = MutableStateFlow<List<Games>>(emptyList())
    val recommendedGames: StateFlow<List<Games>> = _recommendedGames

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news

    init {
        loadHomeData()
    }


    private fun loadHomeData(){
        viewModelScope.launch {
            _categories.value = repository.getCategories()
            _trendingGames.value = repository.getTrendingGames()
            _newGames.value = repository.getNewGames()
            _recommendedGames.value = repository.getRecommendedGames()
            _news.value = repository.getNews()
        }
    }

    fun selectCategory(categoryId : Int) {
        val updatedList = _categories.value.map { category ->
            category.copy(isSelected = category.id == categoryId)
        }
        _categories.value = updatedList
    }


}