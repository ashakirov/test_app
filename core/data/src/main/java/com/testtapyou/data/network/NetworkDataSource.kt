package com.testtapyou.data.network

import com.testtapyou.domain.model.Point

interface NetworkDataSource {
    suspend fun getPoints(count: Int): Result<List<Point>>
}