package com.dezdeqness.tmdb.presentation.features.favourite

import com.dezdeqness.tmdb.core.UiItem
import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import com.dezdeqness.tmdb.presentation.features.shared.action.ActionReducer
import com.dezdeqness.tmdb.presentation.features.shared.composer.UiModelComposer
import com.dezdeqness.tmdb.utils.TestCoroutineDispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class FavouritesViewModelTest {

    @Mock
    private lateinit var uiModelComposer: UiModelComposer

    @Mock
    private lateinit var favouriteRepository: FavouriteRepository

    @Mock
    private lateinit var actionReducer: ActionReducer

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `WHEN list of favourites exists SHOULD show list`() = runTest {
        val list = listOf<MovieEntity>(mock(), mock())
        val uiList = listOf<UiItem>(mock(), mock(), mock())
        `when`(favouriteRepository.getFavourites()).thenReturn(flowOf(list))
        `when`(uiModelComposer.composePage(list)).thenReturn(uiList)

        val viewModel = createViewModel()

        val uiState = viewModel.favouriteState.value

        assertAll(
            { assertTrue(uiState.uiItems.isNotEmpty()) },
            { assertTrue(uiState.uiItems.size == uiList.size) },
        )

    }


    private fun createViewModel() =
        FavouritesViewModel(
            uiModelComposer = uiModelComposer,
            favouriteRepository = favouriteRepository,
            actionReducer = actionReducer,
            coroutineDispatcherProvider = TestCoroutineDispatcherProvider(),
        )
}