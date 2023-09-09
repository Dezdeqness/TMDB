package com.dezdeqness.tmdb.data.service

import com.dezdeqness.tmdb.BuildConfig
import com.dezdeqness.tmdb.data.model.remote.MoviePage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    fun getMoviePage(
        @Query(value = "page") page: Int,
        @Query(value = "sort_by") sortBy: String = "primary_release_date.desc",
        @Query(value = "vote_average.gte") voteAverage: Double = 7.0,
        @Query(value = "vote_count.gte") voteCount: Int = 100,
        @Query(value = "api_key") apiKey: String = BuildConfig.APIKEY,
    ): Call<MoviePage>
}
