package com.dezdeqness.tmdb.presentation.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezdeqness.tmdb.R
import com.dezdeqness.tmdb.presentation.features.favourite.FavouritePage
import com.dezdeqness.tmdb.presentation.features.feed.FeedPage
import com.dezdeqness.tmdb.ui.theme.TmdbTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()

    val state = viewModel.homeState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        val tabIndex = state.value.selectedTab

        val tabs = listOf(
            stringResource(id = R.string.tab_films_title),
            stringResource(id = R.string.tab_favourites_title),
        )

        val pagerState = rememberPagerState(initialPage = tabIndex)

        Tabs(
            tabIndex = tabIndex,
            tabs = tabs,
            onClick = { index ->
                viewModel.onTabClicked(index = index)
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
        )

        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}

@Composable
fun Tabs(
    tabIndex: Int,
    tabs: List<String>,
    onClick: (Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = tabIndex,
        indicator = { tabPositions ->
            if (tabIndex < tabPositions.size) {
                TabRowDefaults.Indicator(
                    height = 2.dp,
                    color = TmdbTheme.colors.indicator,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex])
                )
            }
        },
        containerColor = TmdbTheme.colors.tabContainer,
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(
                        text = title,
                        style = TmdbTheme.typography.tabTitle,
                    )
                },
                selected = tabIndex == index,
                onClick = {
                    onClick(index)
                },
                selectedContentColor = TmdbTheme.colors.tabSelected,
                unselectedContentColor = TmdbTheme.colors.tabUnSelected,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(
    tabs: List<String>,
    pagerState: PagerState,
) {
    HorizontalPager(
        pageCount = tabs.size,
        state = pagerState
    ) { index ->
        when (index) {
            0 -> FeedPage()
            1 -> FavouritePage()
        }

    }
}