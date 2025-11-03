package com.michaelalu.chililabs.data.network

import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.model.DetailResponse
import com.michaelalu.chililabs.data.model.GiphyApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<GiphyApiResponse>

    @GET("gifs/{gif_id}")
    suspend fun getGifs(
        @Path("gif_id") gifId: String,
        @Query("api_key") apiKey: String
    ): Response<DetailResponse>

}