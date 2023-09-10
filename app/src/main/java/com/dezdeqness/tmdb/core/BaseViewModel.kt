package com.dezdeqness.tmdb.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dezdeqness.tmdb.presentation.features.shared.action.ActionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel(), CoroutineScope, ActionListener {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext


    fun launchOnIo(lambda: suspend () -> Unit) =
        launch(coroutineDispatcherProvider.io()) {
            lambda.invoke()
        }

    fun launchOnMain(lambda: suspend () -> Unit) =
        launch(coroutineDispatcherProvider.main()) {
            lambda.invoke()
        }

}
