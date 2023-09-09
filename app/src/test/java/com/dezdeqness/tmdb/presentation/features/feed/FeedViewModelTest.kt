package com.dezdeqness.tmdb.presentation.features.feed

import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.model.MoviePageEntity
import com.dezdeqness.tmdb.domain.repository.MovieRepository
import com.dezdeqness.tmdb.presentation.features.shared.composer.UiModelComposer
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel
import com.dezdeqness.tmdb.utils.TestCoroutineDispatcherProvider
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class FeedViewModelTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var uiModelComposer: UiModelComposer

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN fetching page is success SHOULD show list with no loadings and errors`() =
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

    private fun createViewModel() = FeedViewModel(
        movieRepository = movieRepository,
        uiModelComposer = uiModelComposer,
        coroutineDispatcherProvider = TestCoroutineDispatcherProvider(),
    )
}
