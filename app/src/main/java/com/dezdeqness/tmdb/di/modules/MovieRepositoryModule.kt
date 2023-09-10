package com.dezdeqness.tmdb.di.modules

import com.dezdeqness.tmdb.data.datasource.remote.MovieRemoteDatasource
import com.dezdeqness.tmdb.data.datasource.remote.MovieRemoteDatasourceImpl
import com.dezdeqness.tmdb.data.mapper.MovieMapper
import com.dezdeqness.tmdb.data.repository.MovieRepositoryImpl
import com.dezdeqness.tmdb.data.service.MovieService
import com.dezdeqness.tmdb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MovieRepositoryModule {

    @Provides
    fun provideMovieRemoteDatasourceImpl(
        mapper: MovieMapper,
        movieService: MovieService,
    ): MovieRemoteDatasource = MovieRemoteDatasourceImpl(
        movieService = movieService,
        movieMapper = mapper,
    )

    @Provides
    fun provideMovieRepository(
        datasource: MovieRemoteDatasource,
    ): MovieRepository = MovieRepositoryImpl(datasource)

}
