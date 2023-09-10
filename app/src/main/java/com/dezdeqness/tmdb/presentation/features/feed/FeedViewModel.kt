package com.dezdeqness.tmdb.presentation.features.feed

import com.dezdeqness.tmdb.core.BaseViewModel
import com.dezdeqness.tmdb.core.CoroutineDispatcherProvider
import com.dezdeqness.tmdb.core.UiItem
import com.dezdeqness.tmdb.domain.model.MovieEntity
import com.dezdeqness.tmdb.domain.repository.FavouriteRepository
import com.dezdeqness.tmdb.domain.repository.MovieRepository
import com.dezdeqness.tmdb.presentation.features.shared.action.Action
import com.dezdeqness.tmdb.presentation.features.shared.action.ActionReducer
import com.dezdeqness.tmdb.presentation.features.shared.composer.UiModelComposer
import com.dezdeqness.tmdb.presentation.features.shared.model.LoadMoreUiModel
import com.dezdeqness.tmdb.presentation.features.shared.model.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val uiModelComposer: UiModelComposer,
    private val favouriteRepository: FavouriteRepository,
    private val actionReducer: ActionReducer,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    private val _feedState: MutableStateFlow<FeedState> = MutableStateFlow(FeedState())
    val feedState: StateFlow<FeedState> = _feedState

    private var currentIndexPage = INITIAL_INDEX

    private var rawData: List<MovieEntity> = mutableListOf()

    init {
        _feedState.update {
            _feedState.value.copy(isInitialLoadingVisible = true)
        }

        fetchPage()

        launchOnIo {
            favouriteRepository.getFavourites().collect { list ->
                if (rawData.isNotEmpty()) {

                    val items = ArrayList(_feedState.value.uiItems)

                    val uiItems = items.map { item ->
                        val isPresent = list.firstOrNull { it.id == item.id() } != null
                        (item as? MovieUiModel)?.copy(isFavourite = isPresent) ?: item
                    }

                    handleSuccess(uiItems)
                }
            }
        }
    }

    override fun onActionReceive(action: Action) {
        launchOnIo {
            actionReducer.reduce(action = action, state = rawData)
        }
    }


    fun onLoadMore() {
        currentIndexPage++
        launchOnIo {
            movieRepository
                .getMoviePageRemote(currentIndexPage)
                .onSuccess { page ->
                    rawData += page.items
                    val loadMoreItems = uiModelComposer.composePage(
                        items = page.items,
                        hasNextPage = page.currentPage < page.totalPages,
                    )
                    val filteredItems = _feedState.value.uiItems.filterNot { it == LoadMoreUiModel }
                    handleSuccess((filteredItems + loadMoreItems).distinct())
                }
                .onFailure { exception ->
                    handleError(exception)
                }

        }

    }

    fun onPullDownRefresh() {
        _feedState.update {
            _feedState.value.copy(isPullDownVisible = true)
        }
        currentIndexPage = INITIAL_INDEX
        launchOnIo {
            movieRepository
                .getMoviePageRemote(currentIndexPage)
                .onSuccess { page ->
                    rawData = page.items
                    val uiItems = uiModelComposer.composePage(
                        items = page.items,
                        hasNextPage = page.currentPage < page.totalPages,
                    )
                    handleSuccess(uiItems)
                }
                .onFailure { exception ->
                    handleError(exception)
                }
        }
    }

    private fun fetchPage() {
        launchOnIo {
            movieRepository
                .getMoviePageRemote(currentIndexPage)
                .onSuccess { page ->
                    rawData = page.items
                    val uiItems = uiModelComposer.composePage(
                        items = page.items,
                        hasNextPage = page.currentPage < page.totalPages,
                    )
                    handleSuccess(uiItems)
                }
                .onFailure { exception ->
                    handleError(exception)
                }
        }
    }

    private fun handleSuccess(uiItems: List<UiItem>) {
        launchOnMain {
            _feedState.update {
                _feedState.value.copy(
                    isInitialLoadingVisible = false,
                    isPullDownVisible = false,
                    isErrorVisible = false,
                    uiItems = uiItems,
                )
            }
        }
    }

    private fun handleError(exception: Throwable) {
        println(exception.toString())
        launchOnMain {
            _feedState.update {
                _feedState.value.copy(
                    isInitialLoadingVisible = false,
                    isPullDownVisible = false,
                    isErrorVisible = true,
                )
            }
        }
    }


    companion object {
        private const val INITIAL_INDEX = 1
    }

}
