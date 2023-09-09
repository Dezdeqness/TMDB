package com.dezdeqness.tmdb.domain.model

data class MovieEntity(
    val id: Long,
    val title: String,
    val overview: String,
    val releaseTimeStamp: Long,
    val imageUrl: String,
    val voteAverage: Double,
)
