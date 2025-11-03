package com.michaelalu.chililabs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.getOrAwaitValue
import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.repository.GifRepository
import com.michaelalu.chililabs.ui.fragment.search.SearchViewModel
import com.michaelalu.utils.NetworkUtils
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GifSearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var repository: GifRepository
    private lateinit var networkUtils: NetworkUtils

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        networkUtils = mock()
        searchViewModel = SearchViewModel(repository, networkUtils)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * ✅ Test 1:
     * Should emit error when network is unavailable
     */
    @Test
    fun `searchGifs emits error when no internet`() = runTest {
        whenever(networkUtils.isNetworkAvailable()).thenReturn(false)

        searchViewModel.searchGif("funny cat")
        advanceUntilIdle()

        val value = searchViewModel.searchUiState.getOrAwaitValue()
        println("UiState in test: $value")

        assertTrue(value is SearchViewModel.UiState.Error)
        assertEquals("No internet connection", (value as SearchViewModel.UiState.Error).message)
    }


}
