package com.dezdeqness.tmdb.presentation.features.shared.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dezdeqness.tmdb.core.UiItem
import com.dezdeqness.tmdb.presentation.features.shared.action.Action
import com.dezdeqness.tmdb.presentation.features.shared.model.HeaderUiModel
import com.dezdeqness.tmdb.presentation.features.shared.model.LoadMoreUiModel
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel

private const val LOAD_FACTOR = 0.75f

@Composable
fun ScrollContent(
    modifier: Modifier = Modifier,
    list: List<UiItem>,
    onActionPreformed: (Action) -> Unit,
    onLoadMore: (() -> Unit)? = null,
) {
    val lazyColumnListState = rememberLazyListState()

    if (onLoadMore != null) {
        val shouldStartPaginate = remember {
            derivedStateOf {
                (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    ?: 0) >= (lazyColumnListState.layoutInfo.totalItemsCount * LOAD_FACTOR)
            }
        }

        LaunchedEffect(shouldStartPaginate.value) {
            if (shouldStartPaginate.value) {
                onLoadMore.invoke()
            }
        }
    }

    LazyColumn(
        state = lazyColumnListState,
        modifier = modifier,
    ) {
        items(
            list,
            key = { item -> item.id() }
        ) { item ->

            when (item) {
                is MovieUiModel -> {
                    MovieTile(
                        movieUiModel = item,
                        onActionPreformed = onActionPreformed,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                    )
                }

                is HeaderUiModel -> {
                    HeaderTile(
                        headerUiModel = item,
                        modifier = Modifier.padding(16.dp),
                    )
                }

                is LoadMoreUiModel -> {
                    LoadMoreIndicator()
                }
            }
        }

    }
}
