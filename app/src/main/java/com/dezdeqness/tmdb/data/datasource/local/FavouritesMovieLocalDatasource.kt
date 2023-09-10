package com.dezdeqness.tmdb.data.datasource.local

import com.dezdeqness.tmdb.domain.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface FavouritesMovieLocalDatasource {

    fun getFavourites(): Flow<List<MovieEntity>>

    fun saveFavourite(item: MovieEntity)

    fun deleteFavourite(item: MovieEntity)
    fun isAdded(id: Long): Boolean

}
