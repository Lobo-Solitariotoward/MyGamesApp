package com.example.videogamesapi.data

import com.example.videogamesapi.models.Category
import com.example.videogamesapi.models.Games
import com.example.videogamesapi.models.News

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

    private val mockRecommendedGames = listOf(
        Games(301, "God of War Ragnarok", "Action - Adventure", "https://as01.epimg.net/meristation/imagenes/2022/11/09/noticias/1668002063_051408_1668002131_noticia_normal.jpg"),
        Games(302, "Cyberpunk 2077", "RPG - Action", "https://cdn1.epicgames.com/salesEvent/salesEvent/EGS_Cyberpunk2077_CDProjektRed_S2_1200x1600-4f8f0f4f5e4f4e1f3e6e3b3c6e2e1f3d?h=854&resize=1&w=640"),
        Games(303, "Red Dead Redemption 2", "Action - Adventure", "https://www.rockstargames.com/reddeadredemption2/assets/images/header-social.jpg"),
    )

    private val mockNews = listOf(
        News(401, "Rockstar anuncia retraso de GTA VI...", "Indigo Geek", "24 hours ago", "https://img.asmedia.epimg.net/resizer/v2/TJAZUKPIARGJXOVKEFQE5PY7UE.jpg?auth=258bf88218600c45691db3de88f8c2fbc7d9342b54cb9597d3fa34326e814e36&width=644&height=362&smart=true"),
        News(402, "DLC gratias de Idle Champions of the... ", "EPIC GAMES STORE", "1 day ago", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsScZ46tBWp6_2bOCH2LQ02fLBkISk8JXSbg&s"),
        News(403, "Confirman nuevas Megaevoluciones...", "Nintenderos", "3 days ago", "https://objetos-xlk.estaticos-marca.com/uploads/2025/10/16/68f0aaa9ea889.jpeg"),
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

    fun getRecommendedGames(): List<Games> {
        return mockRecommendedGames
    }

    fun getNews(): List<News> {
        return mockNews
    }

}