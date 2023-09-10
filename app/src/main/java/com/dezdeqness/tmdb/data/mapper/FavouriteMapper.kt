package com.dezdeqness.tmdb.data.mapper

import com.dezdeqness.tmdb.data.model.local.FavouriteMovieLocal
import com.dezdeqness.tmdb.domain.model.MovieEntity
import javax.inject.Inject

class FavouriteMapper @Inject constructor() {

    fun fromEntity(item: MovieEntity) =
        FavouriteMovieLocal(
            id = item.id,
            title = item.title,
            overview = item.overview,
            releaseTimeStamp = item.releaseTimeStamp,
            imageUrl = item.imageUrl,
            voteAverage = item.voteAverage,
        )

    fun fromLocal(item: FavouriteMovieLocal) =
        MovieEntity(
            id = item.id,
            title = item.title,
            overview = item.overview,
            releaseTimeStamp = item.releaseTimeStamp,
            imageUrl = item.imageUrl,
            voteAverage = item.voteAverage,
        )

}
