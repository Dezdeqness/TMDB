package com.dezdeqness.tmdb.presentation.features.shared.model

import com.dezdeqness.tmdb.core.UiItem

data class MovieUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val score: String,
) : UiItem() {
    override fun id() = id
}
