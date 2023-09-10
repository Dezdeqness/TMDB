package com.dezdeqness.tmdb.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dezdeqness.tmdb.data.model.local.FavouriteMovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun getAll(): Flow<List<FavouriteMovieLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavourite(item: FavouriteMovieLocal)

    @Query("DELETE FROM favourites WHERE id = :id")
    fun deleteFavourite(id: Long)

    @Query("SELECT EXISTS(SELECT * FROM favourites WHERE id = :id)")
    fun isAdded(id: Long): Boolean

}
