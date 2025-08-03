package com.testtapyou.data.di

import android.util.Log
import com.testtapyou.data.network.NetworkDataSource
import com.testtapyou.data.network.retrofit.RetrofitNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor {
            Log.d("retrofit:", " $it")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): NetworkDataSource {
        return RetrofitNetwork(converterFactory, okHttpClient)
    }
}