package com.michaelalu.chililabs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.model.DetailResponse
import com.michaelalu.chililabs.data.network.API
import com.michaelalu.chililabs.jni.KeyRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GifRepository@Inject constructor(
    private val api: API,
    private val apiKey:KeyRepository
) {

    fun searchGifs(query: String) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { GifPagingSource(api, query, apiKey.getApiKey()) }
    ).flow

    suspend fun getGif(id: String): Result<DetailResponse> {
        return try {
            val response = api.getGifs(id, apiKey.getApiKey())
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load GIF details"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}