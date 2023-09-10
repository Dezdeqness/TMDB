package com.dezdeqness.tmdb.di.modules

import android.content.Context
import androidx.room.Room
import com.dezdeqness.tmdb.data.database.TmdbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TmdbDatabase::class.java,
            "tmdb"
        ).build()


    @Singleton
    @Provides
    fun provideFavouriteDao(database: TmdbDatabase) = database.favouriteDao()

}
