package com.dezdeqness.tmdb.presentation.features.feed

import com.dezdeqness.tmdb.core.UiItem

data class FeedState(
    val uiItems: List<UiItem> = listOf(),
    val isInitialLoadingVisible: Boolean = false,
    val isPullDownVisible: Boolean = false,
    val isErrorVisible: Boolean = false,
    val hasNextPage: Boolean = false,
)
