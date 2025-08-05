package com.testtapyou.data.network.model

import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @SerializedName("points")
    val points: List<PointDto>
)

data class PointDto(
    @SerializedName("x")
    val x: Float,
    @SerializedName("y")
    val y: Float
)