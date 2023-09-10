package com.dezdeqness.tmdb.presentation.features.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezdeqness.tmdb.presentation.features.feed.FeedViewModel
import com.dezdeqness.tmdb.presentation.features.shared.ui.ScrollContent

@Composable
fun FavouritePage(
    modifier: Modifier = Modifier,
    viewModel: FavouritesViewModel = hiltViewModel(),
) {

    val state = viewModel.favouriteState.collectAsState()

    ScrollContent(
        list = state.value.uiItems,
        onActionPreformed = viewModel::onActionReceive,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)),
    )

}
