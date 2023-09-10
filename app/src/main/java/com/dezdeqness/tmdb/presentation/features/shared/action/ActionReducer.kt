package com.dezdeqness.tmdb.presentation.features.shared.action

import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import javax.inject.Inject

class ActionReducer @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
) {

    fun reduce(action: Action, state: List<MovieEntity>) {
        when (action) {
            is Action.AddFavourite -> {
                state.find { action.id == it.id }?.let { item ->
                    favouriteRepository.saveFavourite(item)
                }
            }

            is Action.RemoveFavourite -> {
                state.find { action.id == it.id }?.let { item ->
                    favouriteRepository.deleteFavourite(item)
                }
            }
        }
    }

}
