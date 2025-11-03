package com.michaelalu.chililabs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.michaelalu.chililabs.data.network.API
import com.michaelalu.chililabs.data.repository.GifRepository
import com.michaelalu.chililabs.jni.KeyRepository
import com.michaelalu.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private val BASE_URL = "https://api.giphy.com/v1/"

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): API {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(API::class.java)
    }


    @Singleton
    @Provides
    fun provideRepository(api: API,keyRepository: KeyRepository): GifRepository {
        return GifRepository(api,keyRepository)
    }


}