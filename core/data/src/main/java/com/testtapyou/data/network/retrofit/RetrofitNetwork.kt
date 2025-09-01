package com.testtapyou.data.network.retrofit

import com.testtapyou.core.utils.runCatchingCancelable
import com.testtapyou.data.network.NetworkDataSource
import com.testtapyou.domain.model.Point
import okhttp3.OkHttpClient
import okio.IOException
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitNetwork(
    private val converterFactory: Converter.Factory,
    private val okHttpClient: OkHttpClient
) : NetworkDataSource {

    private val service by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
        retrofit.create(TapYouService::class.java)
    }

    override suspend fun getPoints(count: Int): Result<List<Point>> {
        val result = runCatchingCancelable { service.points(count) }
        return if (result.isSuccess) {
            val response = result.getOrThrow()
            if (response.isSuccessful) {
                Result.success(response.body()!!.points.map { Point(it.x, it.y) })
            } else {
                Result.failure(IOException("response code: ${response.code()}"))
            }
        } else {
            Result.failure(result.exceptionOrNull() ?: IOException("error"))
        }
    }

    companion object {
        const val BASE_URL = "https://hr-challenge.dev.tapyou.com/"
    }
}
