package com.testtapyou.network

import com.testtapyou.network.data.Point

interface NetworkDataSource {
    suspend fun getPoints(count: Int): Result<List<Point>>
}