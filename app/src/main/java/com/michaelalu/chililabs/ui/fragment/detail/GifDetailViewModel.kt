package com.michaelalu.chililabs.ui.fragment.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.model.DetailResponse
import com.michaelalu.chililabs.data.network.API
import com.michaelalu.chililabs.data.repository.GifPagingSource
import com.michaelalu.chililabs.data.repository.GifRepository
import com.michaelalu.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject

@HiltViewModel
class GifDetailViewModel @Inject constructor(
    private val repository: GifRepository,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val apiKey = "xJxDd4LWkP00ipATzkDsjMDnk8cCsysb"


    private val _gifDetailUiState = MutableLiveData<UiState<DetailResponse>>()
    val gifDetailUiState: LiveData<UiState<DetailResponse>> = _gifDetailUiState


    fun getDetailGif(id: String) = viewModelScope.launch {
        if (!networkUtils.isNetworkAvailable()) {
            _gifDetailUiState.postValue(UiState.Error("No internet connection"))
            return@launch
        }

        try {
            _gifDetailUiState.postValue(UiState.Loading)

            val result = repository.getGif(id)

            result.onSuccess { data ->
                _gifDetailUiState.postValue(UiState.Success(data))
            }.onFailure { exception ->
                _gifDetailUiState.postValue(UiState.Error(exception.message ?: "Something went wrong"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            _gifDetailUiState.postValue(UiState.Error(e.message ?: "Something went wrong"))
        }
    }


    sealed class UiState<out T> {
        object Loading : UiState<Nothing>()
        data class Success<out T>(val data: T) : UiState<T>()
        data class Error(val message: String) : UiState<Nothing>()
    }
}


