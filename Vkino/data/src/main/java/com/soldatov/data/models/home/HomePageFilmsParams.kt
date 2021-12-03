package com.soldatov.data.models.home

data class HomePageFilmsParams(
    val chosenCategories: String?,
    val chosenGenres: String?,
    val chosenCountries: String?,
    val chosenActors: String?,
    val chosenQualities: String?,
    val chosenYears: String?,
    val page: Int = 1,
    val orderBy: String,
    val order: String
)
