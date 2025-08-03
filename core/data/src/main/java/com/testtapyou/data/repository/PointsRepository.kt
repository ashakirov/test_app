package com.testtapyou.data.repository

import com.testtapyou.data.network.model.Point

interface PointsRepository {
    suspend fun fetchPoints(count: Int): Result<List<Point>>
    suspend fun getPoints(count: Int): List<Point>
    suspend fun save(points: List<Point>)
    suspend fun clear()
}