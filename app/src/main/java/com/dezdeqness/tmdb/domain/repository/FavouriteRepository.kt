package com.dezdeqness.tmdb.domain.repository

import com.dezdeqness.tmdb.domain.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun getFavourites(): Flow<List<MovieEntity>>

    fun saveFavourite(item: MovieEntity)

    fun deleteFavourite(item: MovieEntity)

    fun isAdded(id: Long): Boolean

}
