package com.testtapyou.data.database

import com.testtapyou.data.network.model.Point

interface PointsDataStore {
    suspend fun getPoints(): List<Point>
    suspend fun deleteAll()
    suspend fun save(points: List<Point>)
}