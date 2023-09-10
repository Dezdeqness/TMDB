package com.dezdeqness.tmdb.presentation.features.shared.action

import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@ExtendWith(MockitoExtension::class)
class ActionReducerTest {

    private lateinit var actionReducer: ActionReducer

    @Mock
    private lateinit var favouriteRepository: FavouriteRepository

    @Before
    fun setUp() {
        actionReducer = ActionReducer(favouriteRepository = favouriteRepository)
    }

    @Test
    fun `WHEN received AddFavourite SHOULD add favourite`() {
        val id: Long = 1
        val action = Action.AddFavourite(id = id)
        actionReducer.reduce(action, getListOfMovies())

        verify(
            favouriteRepository,
            times(1)
        ).saveFavourite(getListOfMovies().first { it.id == id })
    }

    @Test
    fun `WHEN received RemoveFavourite SHOULD remove favourite`() {
        val id: Long = 1
        val action = Action.RemoveFavourite(id = id)
        actionReducer.reduce(action, getListOfMovies())

        verify(
            favouriteRepository,
            times(1)
        ).deleteFavourite(getListOfMovies().first { it.id == id })
    }

    private fun getListOfMovies() = listOf(
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

}