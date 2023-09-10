package com.dezdeqness.tmdb.presentation.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dezdeqness.tmdb.R
import com.dezdeqness.tmdb.presentation.features.favourite.FavouritePage
import com.dezdeqness.tmdb.presentation.features.feed.FeedPage
import com.dezdeqness.tmdb.ui.theme.TmdbTheme

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val state = viewModel.homeState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        val tabIndex = state.value.selectedTab

        val tabs = listOf(
            stringResource(id = R.string.tab_films_title),
            stringResource(id = R.string.tab_favourites_title),
        )

        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                val index = state.value.selectedTab
                if (index < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        height = 2.dp,
                        color = TmdbTheme.colors.indicator,
                        modifier = Modifier.tabIndicatorOffset(tabPositions[index])
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
                        viewModel.onTabClicked(index = index)
                    },
                    selectedContentColor = TmdbTheme.colors.tabSelected,
                    unselectedContentColor = TmdbTheme.colors.tabUnSelected,
                )
            }
        }
        when (tabIndex) {
            0 -> FeedPage()
            1 -> FavouritePage()
        }
    }
}
