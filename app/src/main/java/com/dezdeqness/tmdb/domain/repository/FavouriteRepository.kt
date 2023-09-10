package com.dezdeqness.tmdb.domain.repository

import com.dezdeqness.tmdb.domain.model.MovieEntity

interface FavouriteRepository {

    fun getFavourites(): List<MovieEntity>

    fun saveFavourite(item: MovieEntity)

    fun deleteFavourite(item: MovieEntity)

}
