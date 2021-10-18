package com.soldatov.data.models.film

data class TranslationData(
    val id: Long,
    val slug: String,
    val title: String?,
    val short_title: String?,
    val smart_title: String?,
    val shorter_title: String?
)
