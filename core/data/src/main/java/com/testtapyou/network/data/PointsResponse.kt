package com.testtapyou.network.data

import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @SerializedName("points")
    val points: List<Point>
)