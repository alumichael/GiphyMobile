package com.michaelalu.chililabs.ui.fragment.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.repository.GifRepository
import com.michaelalu.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GifRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {


    // Keep UI state
    private val _searchUiState = MutableLiveData<UiState<PagingData<Data>>>()
    val searchUiState: LiveData<UiState<PagingData<Data>>> = _searchUiState

    // Query state
    private val queryFlow = MutableStateFlow("")

    private var searchJob: Job? = null

    init {
        observeQueryFlow()
    }

    fun searchGif(query: String) {
        queryFlow.value = query.trim()
    }

    private fun observeQueryFlow() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            queryFlow
                .debounce(400L) // Wait for user to stop typing
                .distinctUntilChanged()
                .filter { query -> query.isNotBlank() }
                .flatMapLatest { query ->
                    // Stop if no internet
                    if (!networkUtils.isNetworkAvailable()) {
                        _searchUiState.postValue(UiState.Error("No internet connection"))
                        return@flatMapLatest kotlinx.coroutines.flow.emptyFlow()
                    }


                    // Start loading
                    _searchUiState.postValue(UiState.Loading)

                    // Return the flow directly
                    repository.searchGifs(query).cachedIn(viewModelScope)
                }
                .collectLatest { pagingData ->
                    _searchUiState.postValue(UiState.Success(pagingData))
                }
        }
    }

    sealed class UiState<out T> {
        object Idle : UiState<Nothing>()
        object Empty : UiState<Nothing>()
        object Loading : UiState<Nothing>()
        data class Success<out T>(val data: T) : UiState<T>()
        data class Error(val message: String) : UiState<Nothing>()
    }
}
