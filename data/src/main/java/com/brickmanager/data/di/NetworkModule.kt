package com.brickmanager.data.di

import com.brickmanager.data.remote.RebrickableApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt module that provides network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://rebrickable.com/api/v3/"

    /**
     * Provides a singleton instance of the [Retrofit] client.
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides a singleton instance of the [RebrickableApiService].
     */
    @Singleton
    @Provides
    fun provideRebrickableApiService(retrofit: Retrofit): RebrickableApiService {
        return retrofit.create(RebrickableApiService::class.java)
    }
}