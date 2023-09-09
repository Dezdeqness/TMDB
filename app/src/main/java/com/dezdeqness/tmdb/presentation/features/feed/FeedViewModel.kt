package com.dezdeqness.tmdb.presentation.features.feed

import com.dezdeqness.tmdb.core.BaseViewModel
import com.dezdeqness.tmdb.core.CoroutineDispatcherProvider
import com.dezdeqness.tmdb.core.UiItem
import com.dezdeqness.tmdb.domain.repository.MovieRepository
import com.dezdeqness.tmdb.presentation.features.shared.composer.UiModelComposer
import com.dezdeqness.tmdb.presentation.features.shared.model.LoadMoreUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val uiModelComposer: UiModelComposer,
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : BaseViewModel(coroutineDispatcherProvider = coroutineDispatcherProvider) {

    private val _feedState: MutableStateFlow<FeedState> = MutableStateFlow(FeedState())
    val feedState: StateFlow<FeedState> = _feedState

    private var currentIndexPage = INITIAL_INDEX

    init {
        _feedState.update {
            _feedState.value.copy(isInitialLoadingVisible = true)
        }

        fetchPage()
    }

    fun onLoadMore() {
        currentIndexPage++
        launchOnIo {
            movieRepository
                .getMoviePageRemote(currentIndexPage)
                .onSuccess { page ->
                    val loadMoreItems = uiModelComposer.composePage(
                        items = page.items,
                        hasNextPage = page.currentPage < page.totalPages,
                    )
                    val filteredItems = _feedState.value.uiItems.filterNot { it == LoadMoreUiModel }
                    handleSuccess(filteredItems + loadMoreItems)
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

    fun onChangeFavouriteButtonClicked(id: Long) {

    }

    fun onShareButtonClicked(id: Long) {

    }

    private fun fetchPage() {
        launchOnIo {
            movieRepository
                .getMoviePageRemote(currentIndexPage)
                .onSuccess { page ->
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