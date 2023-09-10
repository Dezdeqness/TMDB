package com.dezdeqness.tmdb.di.modules

import com.dezdeqness.tmdb.data.dao.FavouriteDao
import com.dezdeqness.tmdb.data.datasource.local.FavouritesMovieLocalDatasource
import com.dezdeqness.tmdb.data.datasource.local.FavouritesMovieLocalDatasourceImpl
import com.dezdeqness.tmdb.data.mapper.FavouriteMapper
import com.dezdeqness.tmdb.data.repository.FavouriteRepositoryImpl
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouriteRepositoryModule {

    @Provides
    fun provideFavouritesMovieLocalDatasource(
        mapper: FavouriteMapper,
        dao: FavouriteDao,
    ): FavouritesMovieLocalDatasource = FavouritesMovieLocalDatasourceImpl(
        favouriteDao = dao,
        favouriteMapper = mapper,
    )

    @Singleton
    @Provides
    fun provideFavouriteRepository(
        datasource: FavouritesMovieLocalDatasource,
    ): FavouriteRepository = FavouriteRepositoryImpl(datasource)

}
