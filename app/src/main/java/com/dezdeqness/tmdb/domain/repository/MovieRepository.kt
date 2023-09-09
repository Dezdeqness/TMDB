package com.dezdeqness.tmdb.domain.repository

import com.dezdeqness.tmdb.domain.model.MoviePageEntity

interface MovieRepository {

    fun getMoviePageRemote(page: Int): Result<MoviePageEntity>

}
