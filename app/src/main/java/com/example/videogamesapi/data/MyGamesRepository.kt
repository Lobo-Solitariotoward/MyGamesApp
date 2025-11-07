package com.example.videogamesapi.data

import com.example.videogamesapi.models.Category
import com.example.videogamesapi.models.Games

class MyGamesRepository (){
    private val mockCategories = listOf(
        Category(1, "Action", true),
        Category(2, "Shooter"),
        Category(3, "MOBA"),
        Category(4, "Strategy"),
        Category(5, "Adventure")
    )

    private val mockTrendingGames = listOf(
        Games(101, "GTAV", "Tactical Shooter - FPS", "https://img.chilango.com/2013/09/franklin-trevor-y-michael-estan-listos-para-hacerte-vibrar.jpg"),
        Games(102, "Mortal Kombat 11", "Fight - Violence", "https://static.wikia.nocookie.net/mortalkombatfanon/images/6/61/MK11_Cover_Art.jpg/revision/latest?cb=20201118055020"),
        Games(103, "Tekken 8", "3D Fight", "https://locosxlosjuegos.com/wp-content/uploads/2024/02/TEKKEN8_Header_mobile_2.jpg")
    )

    private val mockNewGames = listOf(
        Games(201, "The Last Of Us Part I", "Action - Adventure - Shooter", "https://i.blogs.es/28a4e4/the-last-of-us-remake-ps5-pc/1366_2000.jpeg"),
        Games(202, "Horizon Zero Dawn", "Action-RPG", "https://cdna.artstation.com/p/assets/images/images/085/146/714/large/materia-folie2.jpg?1740070140"),
        Games(203, "Elden Ring", "Souls-like RPG", "https://www.masgamers.com/wp-content/uploads/2022/01/image-30.jpeg"),
    )

    fun getCategories(): List<Category> {
        return mockCategories
    }

    fun getTrendingGames(): List<Games> {
        return mockTrendingGames
    }

    fun getNewGames(): List<Games> {
        return mockNewGames
    }

}