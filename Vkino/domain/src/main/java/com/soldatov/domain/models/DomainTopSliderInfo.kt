package com.soldatov.domain.models

data class DomainTopSliderInfo(
    val filmId: Long,
    val genres: List<String>,
    val poster: String?,
    val title: String?,
    val year: Int?
)
