package com.testtapyou.domain.repository

import com.testtapyou.domain.model.Point

interface PointsRepository {
    suspend fun fetchPoints(count: Int): Result<List<Point>>
    suspend fun getPoints(): List<Point>
    suspend fun save(points: List<Point>)
    suspend fun clear()
}