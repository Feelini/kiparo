package com.soldatov.data.models.film

import com.soldatov.data.models.filter.ActorData
import com.soldatov.data.models.filter.GenreData

data class FilmData(
    val film_id: Long,
    val ru_title: String?,
    val orig_title: String?,
    val en_title: String?,
    val description: String?,
    val cat_id: Int,
    val iframe_src: String,
    val trailer: String?,
    val duration: String?,
    val poster: String?,
    val frames: List<String>,
    val world_premiere: String?,
    val budget: String?,
    val rating_kinopoisk: Double?,
    val rating_imdb: Double?,
    val rating: Double,
    val updated: String?,
    val created: String?,
    val year: Int?,
    val actors: List<ActorData>,
    val composers: List<ComposerData>,
    val countries: List<CountriesData>,
    val directors: List<DirectorData>,
    val genres: List<GenreData>,
    val last_episode: LastEpisodeData,
    val qualities: List<QualityData>,
//    val reviews: List<>,
    val translations: List<TranslationData>,
    val imdb_id: String?,
    val kinopoisk_id: String?,
    val videocdn_id: String?,
    val views: Int
)
