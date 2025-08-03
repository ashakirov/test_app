package com.testtapyou.data.network

import com.testtapyou.data.network.model.Point

interface NetworkDataSource {
    suspend fun getPoints(count: Int): Result<List<Point>>
}