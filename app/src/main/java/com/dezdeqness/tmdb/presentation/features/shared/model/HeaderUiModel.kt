package com.dezdeqness.tmdb.presentation.features.shared.model

import com.dezdeqness.tmdb.core.UiItem

data class HeaderUiModel(
    val id: Long,
    val date: String,
) : UiItem() {
    override fun id() = id
}
