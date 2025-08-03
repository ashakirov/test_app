package com.testtapyou.data.repository

import com.testtapyou.data.network.NetworkDataSource
import com.testtapyou.data.network.model.Point

class PointsRepositoryImpl(
    private val remoteDataSource: NetworkDataSource
) : PointsRepository {

    override suspend fun fetchPoints(count: Int): Result<List<Point>> {
        return remoteDataSource.getPoints(count)
    }

    override suspend fun getPoints(count: Int): Result<List<Point>> {
        TODO("Not yet implemented")
    }
}