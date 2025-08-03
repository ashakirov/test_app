package com.testtapyou.data.repository

import com.testtapyou.data.database.PointsDataStore
import com.testtapyou.data.network.NetworkDataSource
import com.testtapyou.data.network.model.Point

class PointsRepositoryImpl(
    private val remoteDataSource: NetworkDataSource,
    private val localDataStore: PointsDataStore
) : PointsRepository {

    override suspend fun fetchPoints(count: Int): Result<List<Point>> {
        return remoteDataSource.getPoints(count)
    }

    override suspend fun getPoints(count: Int): List<Point> {
        return localDataStore.getPoints()
    }

    override suspend fun save(points: List<Point>) {
        localDataStore.save(points)
    }

    override suspend fun clear() {
        localDataStore.deleteAll()
    }
}