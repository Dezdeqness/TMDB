package com.dezdeqness.tmdb.data.repository

import com.dezdeqness.tmdb.data.datasource.remote.MovieRemoteDatasource
import com.dezdeqness.tmdb.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieRemoteDatasource: MovieRemoteDatasource,
) : MovieRepository {
    override fun getMoviePageRemote(page: Int) =
        movieRemoteDatasource.getMoviePage(page)
}