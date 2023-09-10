package com.dezdeqness.tmdb.presentation.features.shared.composer

import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import com.dezdeqness.tmdb.presentation.features.shared.model.HeaderUiModel
import com.dezdeqness.tmdb.presentation.features.shared.model.LoadMoreUiModel
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension

@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class UiModelComposerTest {

    private lateinit var composer: UiModelComposer

    @Mock
    private lateinit var favouriteRepository: FavouriteRepository

    @Before
    fun setUp() {
        composer = UiModelComposer(favouriteRepository = favouriteRepository)
    }

    @Test
    fun `WHEN list contains two items from the same date AND no next page SHOULD be two items with one date header`() {

        val items = listOf(
            MovieEntity(
                id = 1,
                title = "title1",
                overview = "overview1",
                releaseTimeStamp = 1694349114000, //  Sunday, September 10, 2023 12:31:54 PM
                imageUrl = "imageUrl1",
                voteAverage = 7.0,
            ),
            MovieEntity(
                id = 2,
                title = "title2",
                overview = "overview2",
                releaseTimeStamp = 1693571514000, // Friday, September 1, 2023 12:31:54 PM
                imageUrl = "imageUrl2",
                voteAverage = 7.0,
            ),
        )

        val uiItems = composer.composePage(
            items = items,
            hasNextPage = false,
        )

        assertAll(
            { assertTrue(uiItems.filterIsInstance<MovieUiModel>().size == 2) },
            { assertTrue(uiItems.filterIsInstance<LoadMoreUiModel>().isEmpty()) },
            { assertTrue(uiItems.filterIsInstance<HeaderUiModel>().size == 1) },
            {
                assertTrue(
                    uiItems.filterIsInstance<HeaderUiModel>().firstOrNull()?.date == "Sep 2023"
                )
            },
        )
    }

    @Test
    fun `WHEN list contains two items from the same date AND next page SHOULD be two items with one date header and load more`() {

        val items = listOf(
            MovieEntity(
                id = 1,
                title = "title1",
                overview = "overview1",
                releaseTimeStamp = 1694349114000, //  Sunday, September 10, 2023 12:31:54 PM
                imageUrl = "imageUrl1",
                voteAverage = 7.0,
            ),
            MovieEntity(
                id = 2,
                title = "title2",
                overview = "overview2",
                releaseTimeStamp = 1693571514000, // Friday, September 1, 2023 12:31:54 PM
                imageUrl = "imageUrl2",
                voteAverage = 7.0,
            ),
        )

        val uiItems = composer.composePage(
            items = items,
            hasNextPage = true,
        )

        assertAll(
            { assertTrue(uiItems.filterIsInstance<MovieUiModel>().size == 2) },
            { assertTrue(uiItems.filterIsInstance<LoadMoreUiModel>().size == 1) },
            { assertTrue(uiItems.filterIsInstance<HeaderUiModel>().size == 1) },
            {
                assertTrue(
                    uiItems.filterIsInstance<HeaderUiModel>().firstOrNull()?.date == "Sep 2023"
                )
            },
        )
    }

    @Test
    fun `WHEN list contains two items from the different date AND no next page SHOULD be two items with two date headers`() {

        val items = listOf(
            MovieEntity(
                id = 1,
                title = "title1",
                overview = "overview1",
                releaseTimeStamp = 1694349114000, //  Sunday, September 10, 2023 12:31:54 PM
                imageUrl = "imageUrl1",
                voteAverage = 7.0,
            ),
            MovieEntity(
                id = 2,
                title = "title2",
                overview = "overview2",
                releaseTimeStamp = 1690893114000, // Tuesday, August 1, 2023 12:31:54 PM
                imageUrl = "imageUrl2",
                voteAverage = 7.0,
            ),
        )

        val uiItems = composer.composePage(
            items = items,
            hasNextPage = false,
        )

        assertAll(
            { assertTrue(uiItems.filterIsInstance<MovieUiModel>().size == 2) },
            { assertTrue(uiItems.filterIsInstance<LoadMoreUiModel>().isEmpty()) },
            { assertTrue(uiItems.filterIsInstance<HeaderUiModel>().size == 2) },
            {
                assertTrue(
                    uiItems.filterIsInstance<HeaderUiModel>().find { it.date == "Sep 2023" } != null
                )
            },
            {
                assertTrue(
                    uiItems.filterIsInstance<HeaderUiModel>().find { it.date == "Aug 2023" } != null
                )
            },
        )
    }


}
