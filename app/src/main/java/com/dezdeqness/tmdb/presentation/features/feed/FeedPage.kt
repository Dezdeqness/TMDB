package com.dezdeqness.tmdb.presentation.features.feed

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezdeqness.tmdb.presentation.features.shared.ui.ErrorContent
import com.dezdeqness.tmdb.presentation.features.shared.ui.ScrollContent
import com.dezdeqness.tmdb.ui.theme.TmdbTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedPage(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {

            val state = viewModel.feedState.collectAsState()

            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.value.isPullDownVisible,
                onRefresh = {
                    viewModel.onPullDownRefresh()
                },
            )

            val currentState = state.value

            when {
                currentState.isInitialLoadingVisible -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                currentState.isErrorVisible -> {
                    ErrorContent(
                        modifier = Modifier.pullRefresh(pullRefreshState)
                    )

                    PullRefreshIndicator(
                        refreshing = currentState.isPullDownVisible,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }

                else -> {
                    ScrollContent(
                        list = currentState.uiItems,
                        onLoadMore = viewModel::onLoadMore,
                        onActionPreformed = viewModel::onActionReceive,
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                            .background(TmdbTheme.colors.backgroundLight),
                    )
                    PullRefreshIndicator(
                        refreshing = currentState.isPullDownVisible,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            }

            currentState.events.forEach { event ->
                when (event) {
                    is ErrorEvent -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(event.errorMessage)
                        }
                    }
                }

                viewModel.onEventConsumed(event)
            }

        }
    }

}
