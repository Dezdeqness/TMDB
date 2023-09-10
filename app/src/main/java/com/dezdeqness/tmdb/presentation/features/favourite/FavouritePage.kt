package com.dezdeqness.tmdb.presentation.features.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezdeqness.tmdb.presentation.features.shared.ui.EmptyState
import com.dezdeqness.tmdb.presentation.features.shared.ui.ScrollContent
import com.dezdeqness.tmdb.ui.theme.TmdbTheme

@Composable
fun FavouritePage(
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel = hiltViewModel(),
) {

    val state = viewModel.favouriteState.collectAsState()

    val currentModifier = modifier
        .fillMaxSize()
        .background(TmdbTheme.colors.backgroundLight)

    if (state.value.uiItems.isEmpty()) {
        EmptyState(
            modifier = currentModifier,
        )
    } else {
        ScrollContent(
            list = state.value.uiItems,
            onActionPreformed = viewModel::onActionReceive,
            modifier = currentModifier,
        )
    }

}
