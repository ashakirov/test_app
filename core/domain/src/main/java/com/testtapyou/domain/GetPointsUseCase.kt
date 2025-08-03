package com.testtapyou.domain

import com.testtapyou.data.network.model.Point
import com.testtapyou.data.repository.PointsRepository
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(
    private val repository: PointsRepository
) {
    suspend fun execute(): List<Point> {
        return repository.getPoints()
    }
}