package com.soldatov.domain.models.favourite

enum class Categories(val id: Int, val value: String) {
    FILMS(1, "Фильмы"),
    ANIME(2, "Аниме"),
    SERIALS(3, "Сериалы"),
    ANIME_SERIALS(4, "Сериалы аниме"),
    TV(5, "ТВ шоу");

    companion object{
        fun getCategoryByName(categoryName: String): Categories{
            return when(categoryName){
                FILMS.value -> FILMS
                ANIME.value -> ANIME
                SERIALS.value -> SERIALS
                ANIME_SERIALS.value -> ANIME_SERIALS
                else -> TV
            }
        }
    }
}