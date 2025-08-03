package com.testtapyou.data.repository

import com.testtapyou.data.network.model.Point

interface PointsRepository {
    suspend fun fetchPoints(count: Int): Result<List<Point>>
    suspend fun getPoints(count: Int): Result<List<Point>>
}