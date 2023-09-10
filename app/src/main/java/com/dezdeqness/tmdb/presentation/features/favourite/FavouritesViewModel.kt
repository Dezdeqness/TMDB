package com.dezdeqness.tmdb.presentation.features.favourite

import com.dezdeqness.tmdb.core.BaseViewModel
import com.dezdeqness.tmdb.core.CoroutineDispatcherProvider
import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import com.dezdeqness.tmdb.presentation.features.shared.action.Action
import com.dezdeqness.tmdb.presentation.features.shared.action.ActionListener
import com.dezdeqness.tmdb.presentation.features.shared.action.ActionReducer
import com.dezdeqness.tmdb.presentation.features.shared.composer.UiModelComposer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouriteRepository: FavouriteRepository,
    private val uiModelComposer: UiModelComposer,
    private val actionReducer: ActionReducer,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    private val _favouriteState: MutableStateFlow<FavouriteState> =
        MutableStateFlow(FavouriteState())
    val favouriteState: StateFlow<FavouriteState> = _favouriteState

    private var rawData: List<MovieEntity> = mutableListOf()

    init {
        launchOnIo {
            favouriteRepository.getFavourites().collect { list ->
                rawData = list
                val uiItems = uiModelComposer.composePage(list)
                launchOnMain {
                    _favouriteState.update {
                        _favouriteState.value.copy(
                            uiItems = uiItems
                        )
                    }
                }
            }
        }
    }

    override fun onActionReceive(action: Action) {
        launchOnIo {
            actionReducer.reduce(action = action, state = rawData)
        }

    }

}