package com.dezdeqness.tmdb.data.datasource.remote

import com.dezdeqness.tmdb.data.mapper.MovieMapper
import com.dezdeqness.tmdb.data.service.MovieService
import com.dezdeqness.tmdb.domain.model.MoviePageEntity

class MovieRemoteDatasourceImpl(
    private val movieService: MovieService,
    private val movieMapper: MovieMapper,
) : MovieRemoteDatasource {
    override fun getMoviePage(page: Int): Result<MoviePageEntity> = tryWithCatch {
        val response = movieService.getMoviePage(page = page).execute()

        val body = response.body()

        if (response.isSuccessful && body != null) {

            Result.success(
                MoviePageEntity(
                    currentPage = body.page,
                    items = body.results.map(movieMapper::fromResponse),
                    totalPages = body.totalPages,
                )
            )
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    private fun <T> tryWithCatch(block: () -> Result<T>) = try {
        block()
    } catch (exception: Throwable) {
        Result.failure(exception)
    }
}