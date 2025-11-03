package com.michaelalu.chililabs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.getOrAwaitValue
import com.michaelalu.chililabs.data.model.DetailResponse
import com.michaelalu.chililabs.data.model.GifData
import com.michaelalu.chililabs.data.model.GifImages
import com.michaelalu.chililabs.data.repository.GifRepository
import com.michaelalu.chililabs.ui.fragment.detail.GifDetailViewModel
import com.michaelalu.utils.NetworkUtils
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class GifDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: GifDetailViewModel
    private lateinit var repository: GifRepository
    private lateinit var networkUtils: NetworkUtils

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(GifRepository::class.java)
        networkUtils = mock(NetworkUtils::class.java)
        viewModel = GifDetailViewModel(repository,networkUtils)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getGifDetail emits success when repository returns data`() = runTest {
        // GIVEN
        val fakeGif = GifData(
            id = "123",
            title = "LOL",
            username = "michaelAlu",
            rating = "g",
            import_datetime = "2025-11-02",
            images = GifImages(original = null)
        )
        val fakeResponse = DetailResponse(data = fakeGif)
        `when`(networkUtils.isNetworkAvailable()).thenReturn(true)
        `when`(repository.getGif("123"))
            .thenReturn(Result.success(fakeResponse))

        // WHEN
        viewModel.getDetailGif("123")
        advanceUntilIdle()

        // THEN
        val value = viewModel.gifDetailUiState.getOrAwaitValue()
        assertTrue(value is GifDetailViewModel.UiState.Success)

        val success = value as GifDetailViewModel.UiState.Success
        assertEquals(fakeGif, success.data.data) // unwrap DetailResponse.data
    }


    @Test
    fun `getGifDetail emits error when repository throws exception`() = runTest {
        `when`(networkUtils.isNetworkAvailable()).thenReturn(true)
        `when`(repository.getGif(anyString()))
            .thenThrow(RuntimeException("Server error"))

        viewModel.getDetailGif("123")
        advanceUntilIdle()

        val value = viewModel.gifDetailUiState.getOrAwaitValue()
        assertTrue(value is GifDetailViewModel.UiState.Error)
        assertEquals("Server error", (value as GifDetailViewModel.UiState.Error).message)
    }



    @Test
    fun `getGifDetail emits error when no internet connection`() = runTest {
        `when`(networkUtils.isNetworkAvailable()).thenReturn(false)

        viewModel.getDetailGif("123")
        advanceUntilIdle()

        val value = viewModel.gifDetailUiState.getOrAwaitValue()
        assertTrue(value is GifDetailViewModel.UiState.Error)
        assertEquals("No internet connection", (value as GifDetailViewModel.UiState.Error).message)
    }
}
