package com.dezdeqness.tmdb.data.repository

import com.dezdeqness.tmdb.data.datasource.local.FavouritesMovieLocalDatasource
import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository

class FavouriteRepositoryImpl(
    private val favouritesMovieLocalDatasource: FavouritesMovieLocalDatasource,
) : FavouriteRepository {
    override fun getFavourites() = favouritesMovieLocalDatasource.getFavourites()

    override fun saveFavourite(item: MovieEntity) {
        favouritesMovieLocalDatasource.saveFavourite(item)
    }

    override fun deleteFavourite(item: MovieEntity) {
        favouritesMovieLocalDatasource.deleteFavourite(item)
    }

    override fun isAdded(id: Long) = favouritesMovieLocalDatasource.isAdded(id)

}
