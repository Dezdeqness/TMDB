package com.dezdeqness.tmdb.presentation.features.feed

import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.model.MoviePageEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import com.dezdeqness.tmdb.domain.repository.MovieRepository
import com.dezdeqness.tmdb.presentation.features.shared.action.ActionReducer
import com.dezdeqness.tmdb.presentation.features.shared.composer.UiModelComposer
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel
import com.dezdeqness.tmdb.utils.TestCoroutineDispatcherProvider
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class FeedViewModelTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var uiModelComposer: UiModelComposer

    @Mock
    private lateinit var favouriteRepository: FavouriteRepository

    @Mock
    private lateinit var actionReducer: ActionReducer

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN initial fetching of page is success SHOULD show list with no loadings and errors`() =
        runTest {
            val initialPage = 1
            val movieItems = listOf<MovieEntity>(
                mock(), mock(), mock(),
            )
            val moviePageEntity = MoviePageEntity(
                currentPage = initialPage,
                items = movieItems,
                totalPages = 2,
            )
            val successResult = Result.success(moviePageEntity)
            val uiItems = listOf<MovieUiModel>(
                mock(), mock(), mock(), mock(), mock(), mock(),
            )

            `when`(favouriteRepository.getFavourites()).thenReturn(flowOf())
            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(successResult)
            `when`(uiModelComposer.composePage(movieItems, true)).thenReturn(uiItems)

            val viewModel = createViewModel()

            val uiState = viewModel.feedState.value

            assertAll(
                { assertTrue(uiState.uiItems.isNotEmpty()) },
                { assertFalse(uiState.isErrorVisible) },
                { assertFalse(uiState.isPullDownVisible) },
                { assertFalse(uiState.isInitialLoadingVisible) },
            )

        }

    @Test
    fun `WHEN initial fetching of page is failure SHOULD show error`() =
        runTest {
            val initialPage = 1

            val errorResult = Result.failure<MoviePageEntity>(Exception())

            `when`(favouriteRepository.getFavourites()).thenReturn(flowOf())
            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(errorResult)

            val viewModel = createViewModel()

            val uiState = viewModel.feedState.value

            assertAll(
                { assertFalse(uiState.uiItems.isNotEmpty()) },
                { assertTrue(uiState.isErrorVisible) },
                { assertFalse(uiState.isPullDownVisible) },
                { assertFalse(uiState.isInitialLoadingVisible) },
            )

        }

    @Test
    fun `WHEN pull down is success SHOULD show refreshed list with no loadings and errors`() =
        runTest {
            val initialPage = 1
            val movieItems = listOf<MovieEntity>(
                mock(), mock(), mock(),
            )
            val moviePageEntity = MoviePageEntity(
                currentPage = initialPage,
                items = movieItems,
                totalPages = 2,
            )
            val successResult = Result.success(moviePageEntity)
            val uiItems = listOf<MovieUiModel>(
                mock(), mock(), mock(), mock(), mock(), mock(),
            )

            `when`(favouriteRepository.getFavourites()).thenReturn(flowOf())
            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(successResult)
            `when`(uiModelComposer.composePage(movieItems, true)).thenReturn(uiItems)

            val viewModel = createViewModel()

            val refreshedMovieItems = listOf<MovieEntity>(
                mock(), mock(),
            )
            val refreshedMoviePageEntity = MoviePageEntity(
                currentPage = initialPage,
                items = refreshedMovieItems,
                totalPages = 3,
            )

            val refreshedSuccessResult = Result.success(refreshedMoviePageEntity)
            val refreshedUiItems = listOf<MovieUiModel>(
                mock(), mock(), mock(),
            )

            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(
                refreshedSuccessResult
            )
            `when`(uiModelComposer.composePage(refreshedMovieItems, true)).thenReturn(
                refreshedUiItems
            )

            viewModel.onPullDownRefresh()

            val uiState = viewModel.feedState.value

            assertAll(
                { assertTrue(uiState.uiItems.isNotEmpty()) },
                { assertTrue(uiState.uiItems.size == refreshedUiItems.size) },
                { assertFalse(uiState.isErrorVisible) },
                { assertFalse(uiState.isPullDownVisible) },
                { assertFalse(uiState.isInitialLoadingVisible) },
            )

        }

    @Test
    fun `WHEN pull down is failure SHOULD show error`() =
        runTest {
            val initialPage = 1
            val movieItems = listOf<MovieEntity>(
                mock(), mock(), mock(),
            )
            val moviePageEntity = MoviePageEntity(
                currentPage = initialPage,
                items = movieItems,
                totalPages = 2,
            )
            val successResult = Result.success(moviePageEntity)
            val uiItems = listOf<MovieUiModel>(
                mock(), mock(), mock(), mock(), mock(), mock(),
            )

            `when`(favouriteRepository.getFavourites()).thenReturn(flowOf())
            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(successResult)
            `when`(uiModelComposer.composePage(movieItems, true)).thenReturn(uiItems)

            val viewModel = createViewModel()

            val errorResult = Result.failure<MoviePageEntity>(Exception())

            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(errorResult)

            viewModel.onPullDownRefresh()

            val uiState = viewModel.feedState.value

            assertAll(
                { assertTrue(uiState.isErrorVisible) },
                { assertFalse(uiState.isPullDownVisible) },
                { assertFalse(uiState.isInitialLoadingVisible) },
            )

        }

    @Test
    fun `WHEN load more is success SHOULD show merged list of first and second pages with no loadings and errors`() =
        runTest {
            val initialPage = 1
            val movieItems = listOf<MovieEntity>(
                mock(), mock(), mock(),
            )
            val moviePageEntity = MoviePageEntity(
                currentPage = initialPage,
                items = movieItems,
                totalPages = 2,
            )
            val successResult = Result.success(moviePageEntity)
            val uiItems = listOf<MovieUiModel>(
                mock(), mock(), mock(), mock(), mock(), mock(),
            )

            `when`(favouriteRepository.getFavourites()).thenReturn(flowOf())
            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(successResult)
            `when`(uiModelComposer.composePage(movieItems, true)).thenReturn(uiItems)

            val viewModel = createViewModel()

            val secondPage = 2

            val secondPageMovieItems = listOf<MovieEntity>(
                mock(), mock(),
            )
            val secondPageMoviePageEntity = MoviePageEntity(
                currentPage = secondPage,
                items = secondPageMovieItems,
                totalPages = 2,
            )

            val secondPageSuccessResult = Result.success(secondPageMoviePageEntity)
            val secondPageUiItems = listOf<MovieUiModel>(
                mock(), mock(), mock(), mock(),
            )

            `when`(movieRepository.getMoviePageRemote(secondPage)).thenReturn(
                secondPageSuccessResult
            )
            `when`(uiModelComposer.composePage(secondPageMovieItems, false)).thenReturn(
                secondPageUiItems
            )

            viewModel.onLoadMore()

            val uiState = viewModel.feedState.value

            assertAll(
                { assertTrue(uiState.uiItems.isNotEmpty()) },
                { assertTrue(uiState.uiItems.size == uiItems.size + secondPageUiItems.size) },
                { assertFalse(uiState.isErrorVisible) },
                { assertFalse(uiState.isPullDownVisible) },
                { assertFalse(uiState.isInitialLoadingVisible) },
            )

        }

    @Test
    fun `WHEN load more is failure SHOULD show error`() =
        runTest {
            val initialPage = 1
            val movieItems = listOf<MovieEntity>(
                mock(), mock(), mock(),
            )
            val moviePageEntity = MoviePageEntity(
                currentPage = initialPage,
                items = movieItems,
                totalPages = 2,
            )
            val successResult = Result.success(moviePageEntity)
            val uiItems = listOf<MovieUiModel>(
                mock(), mock(), mock(), mock(), mock(), mock(),
            )

            `when`(favouriteRepository.getFavourites()).thenReturn(flowOf())
            `when`(movieRepository.getMoviePageRemote(initialPage)).thenReturn(successResult)
            `when`(uiModelComposer.composePage(movieItems, true)).thenReturn(uiItems)

            val viewModel = createViewModel()

            val secondPage = 2

            val errorResult = Result.failure<MoviePageEntity>(Exception())

            `when`(movieRepository.getMoviePageRemote(secondPage)).thenReturn(errorResult)

            viewModel.onLoadMore()

            val uiState = viewModel.feedState.value

            assertAll(
                { assertTrue(uiState.isErrorVisible) },
                { assertFalse(uiState.isPullDownVisible) },
                { assertFalse(uiState.isInitialLoadingVisible) },
            )

        }


    private fun createViewModel() = FeedViewModel(
        movieRepository = movieRepository,
        uiModelComposer = uiModelComposer,
        favouriteRepository = favouriteRepository,
        actionReducer = actionReducer,
        coroutineDispatcherProvider = TestCoroutineDispatcherProvider(),
    )
}
