package com.testtapyou.domain

import com.testtapyou.data.network.model.Point
import com.testtapyou.data.repository.PointsRepository
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