package com.dezdeqness.tmdb.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteMovieLocal(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_timestamp") val releaseTimeStamp: Long,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
)
