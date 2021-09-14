package com.soldatov.domain.models

data class DomainTopSliderInfo(
    val filmId: Long,
    val title: String?,
    val poster: String?,
    val rating: Double?,
    val category: String,
    val genres: List<String>,
    val year: Int?,
    val qualities: List<String>,
    val translations: List<String>,
    val countries: List<String>,
    val duration: String?,

    val actors: List<String>,
    val composers: List<String>,
    val directors: List<String>,

    val description: String?,

    val iframeSrc: String,
    val trailer: String?,
)
