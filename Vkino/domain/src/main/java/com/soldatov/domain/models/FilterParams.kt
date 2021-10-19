package com.soldatov.domain.models

data class FilterParams(
    val chosenCategories: List<Category> = emptyList(),
    val chosenGenres: List<Genre> = emptyList(),
    val chosenCountries: List<Country> = emptyList(),
    val chosenActors: List<Actor> = emptyList(),
    val chosenQualities: List<Quality> = emptyList(),
    val chosenYears: Years? = null,
    var page: Int = 1
)
