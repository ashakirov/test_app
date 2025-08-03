package com.testtapyou.data.network.retrofit

import com.testtapyou.data.network.model.PointsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TapYouService {

    @GET("/api/test/points/")
    suspend fun points(
        @Query("count") revision: Int
    ): Response<PointsResponse>

}