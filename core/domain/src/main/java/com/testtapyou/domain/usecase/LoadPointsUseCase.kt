package com.testtapyou.domain

import com.testtapyou.domain.model.Point
import com.testtapyou.domain.repository.PointsRepository
import javax.inject.Inject

class LoadPointsUseCase @Inject constructor(
    private val repository: PointsRepository
) {

    suspend fun execute(count: Int): Result<List<Point>> {
        val result = repository.fetchPoints(count)
        if (result.isSuccess) {
            val points = result.getOrThrow()
            repository.clear()
            repository.save(points)
        }
        return result
    }
}