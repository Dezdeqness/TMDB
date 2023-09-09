package com.dezdeqness.tmdb.di.modules

import com.dezdeqness.tmdb.core.CoroutineDispatcherProvider
import com.dezdeqness.tmdb.core.CoroutineDispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCoroutinesDispatcher(): CoroutineDispatcherProvider =
        CoroutineDispatcherProviderImpl()

}
