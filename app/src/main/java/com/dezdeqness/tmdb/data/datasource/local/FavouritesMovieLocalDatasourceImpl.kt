package com.dezdeqness.tmdb.data.datasource.local

import com.dezdeqness.tmdb.data.dao.FavouriteDao
import com.dezdeqness.tmdb.data.mapper.FavouriteMapper
import com.dezdeqness.tmdb.domain.model.MovieEntity
import javax.inject.Inject

class FavouritesMovieLocalDatasourceImpl @Inject constructor(
    private val favouriteDao: FavouriteDao,
    private val favouriteMapper: FavouriteMapper,
) : FavouritesMovieLocalDatasource {
    override fun getFavourites() = favouriteDao.getAll().map(favouriteMapper::fromLocal)

    override fun saveFavourite(item: MovieEntity) {
        val local = favouriteMapper.fromEntity(item)
        favouriteDao.saveFavourite(local)
    }

    override fun deleteFavourite(item: MovieEntity) {
        favouriteDao.deleteFavourite(item.id)
    }
}
