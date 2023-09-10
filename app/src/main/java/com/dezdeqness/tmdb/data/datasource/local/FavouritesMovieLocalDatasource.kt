package com.dezdeqness.tmdb.data.datasource.local

import com.dezdeqness.tmdb.domain.model.MovieEntity

interface FavouritesMovieLocalDatasource {

    fun getFavourites(): List<MovieEntity>

    fun saveFavourite(item: MovieEntity)

    fun deleteFavourite(item: MovieEntity)

}
