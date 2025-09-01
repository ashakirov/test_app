package com.testtapyou.data.database

import com.testtapyou.domain.model.Point

interface PointsDataStore {
    suspend fun getPoints(): List<Point>
    suspend fun deleteAll()
    suspend fun save(points: List<Point>)
}