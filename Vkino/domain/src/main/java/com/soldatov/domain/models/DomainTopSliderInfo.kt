package com.soldatov.domain.models

data class DomainTopSliderInfo(
    val filmId: Long,
    val genres: List<String>,
    val poster: String?,
    val ruTitle: String?,
    val engTitle: String?,
    val origTitle: String?,
    val year: Int?
)
