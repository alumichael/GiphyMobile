package com.michaelalu.chililabs.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.network.API


/**
 * A [PagingSource] implementation responsible for fetching paginated GIF data
 * from the Giphy API based on a given search query.
 *
 * This class handles page key calculation, API calls, and converting API responses
 * into [LoadResult] objects for the Android Paging 3 library.
 *
 * @property api The [API] service used to perform network requests.
 * @property query The search keyword for GIF retrieval.
 * @property apiKey The Giphy API key used for authentication.
 *
 * Example usage:
 * ```
 * Pager(
 *     config = PagingConfig(pageSize = 10),
 *     pagingSourceFactory = { GifPagingSource(api, "funny cats", apiKey) }
 * ).flow
 * ```
 */

class GifPagingSource(
    private val api: API,
    private val query: String,
    private val apiKey:String
) : PagingSource<Int, Data>() {


    /**
     * Determines the key to use when the Paging system needs to reload data
     * after content has been invalidated (e.g., configuration change or refresh).
     *
     * @param state The current [PagingState] containing loaded pages and position info.
     * @return The key (page index) to reload from, or null if it cannot be determined.
     */
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    /**
     * Loads a single page of data from the Giphy API.
     *
     * @param params Defines the key (page index) and load size.
     * @return A [LoadResult.Page] on success, or a [LoadResult.Error] on failure.
     *
     * This method:
     * - Uses the provided [query] and [apiKey] to call the Giphy search endpoint.
     * - Calculates next and previous keys for smooth paging.
     * - Handles both success and error responses gracefully.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val currentPage = params.key ?: 0
        val response = api.searchGifs(apiKey, query, currentPage * 10, 10)
        return if (response.isSuccessful) {
            val resp = response.body()!!

            LoadResult.Page(
                resp.data,
                if (currentPage == 0) null else currentPage - 1,
                if (resp.pagination.offset + resp.pagination.count > resp.pagination.total_count) {
                    null
                } else {
                    currentPage + 1
                }
            )
        } else {
            val unknownException = Exception("Some thing went wrong, please try again")
            LoadResult.Error(unknownException)
        }
    }
}