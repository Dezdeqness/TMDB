package com.dezdeqness.tmdb.core

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {

    fun main(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun computation(): CoroutineDispatcher

}
