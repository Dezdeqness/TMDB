package com.dezdeqness.tmdb.data.mapper

import com.dezdeqness.tmdb.BuildConfig
import com.dezdeqness.tmdb.data.model.remote.MovieRemote
import com.dezdeqness.tmdb.domain.model.MovieEntity
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    private val simpleDateFormat: SimpleDateFormat =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun fromResponse(item: MovieRemote) =
        MovieEntity(
            id = item.id,
            title = item.title,
            overview = item.overview,
            releaseTimeStamp = simpleDateFormat.parse(item.releaseDate)?.time ?: 0L,
            imageUrl = BuildConfig.BASE_IMAGE_URL + IMAGE_URL_ATTRIBUTES + item.posterPath,
            voteAverage = item.voteAverage,
        )

    private companion object {
        private const val IMAGE_URL_ATTRIBUTES = "t/p/w780/"
    }

}
