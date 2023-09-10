package com.dezdeqness.tmdb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dezdeqness.tmdb.data.dao.FavouriteDao
import com.dezdeqness.tmdb.data.model.local.FavouriteMovieLocal

@Database(entities = [FavouriteMovieLocal::class], version = 1)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
}
