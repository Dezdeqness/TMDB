package com.dezdeqness.tmdb.data.datasource.remote

import com.dezdeqness.tmdb.domain.model.MoviePageEntity

interface MovieRemoteDatasource {

    fun getMoviePage(page: Int): Result<MoviePageEntity>

}
