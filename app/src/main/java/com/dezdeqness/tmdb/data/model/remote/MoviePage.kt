package com.dezdeqness.tmdb.data.model.remote

import com.squareup.moshi.Json

data class MoviePage(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "results") val results: List<MovieRemote>,
    @field:Json(name = "total_pages") val totalPages: Int,
)
