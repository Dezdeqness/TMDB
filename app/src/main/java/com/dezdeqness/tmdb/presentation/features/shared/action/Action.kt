package com.dezdeqness.tmdb.presentation.features.shared.action

interface Action {
    data class AddFavourite(val id: Long) : Action
    data class RemoveFavourite(val id: Long) : Action
    data class Share(val id: Long) : Action
}
