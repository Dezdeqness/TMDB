package com.dezdeqness.tmdb.domain.model

data class MoviePageEntity(
    val currentPage: Int,
    val items: List<MovieEntity>,
    val totalPages: Int,
)
