package com.testtapyou.domain

import com.testtapyou.domain.model.Point
import com.testtapyou.domain.repository.PointsRepository
import javax.inject.Inject

class GetPointsUseCase @Inject constructor(
    private val repository: PointsRepository
) {
    suspend fun execute(): List<Point> {
        return repository.getPoints()
    }
}