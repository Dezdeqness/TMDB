package com.dezdeqness.tmdb.data.model.remote

import com.squareup.moshi.Json

data class MovieRemote(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "original_title") val title: String,
    @field:Json(name = "overview") val overview: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "poster_path") val posterPath: String,
    @field:Json(name = "vote_average") val voteAverage: Double,
)
