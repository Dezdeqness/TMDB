package com.dezdeqness.tmdb.presentation.features.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    init {
        emitState(index = INITIAL_INDEX)
    }

    fun onTabClicked(index: Int) {
        emitState(index = index)
    }

    private fun emitState(index: Int) {
        _homeState.update {
            _homeState.value.copy(selectedTab = index)
        }
    }


    companion object {
        private const val INITIAL_INDEX = 0
    }

}
