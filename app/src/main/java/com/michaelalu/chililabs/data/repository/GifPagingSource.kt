package com.michaelalu.chililabs.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.network.API

class GifPagingSource(
    private val api: API,
    private val query: String,
    private val apiKey:String
) : PagingSource<Int, Data>() {



    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

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