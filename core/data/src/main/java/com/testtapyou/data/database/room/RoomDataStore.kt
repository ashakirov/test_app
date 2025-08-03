package com.testtapyou.data.database.room

import android.content.Context
import androidx.room.Room
import com.testtapyou.data.database.PointsDataStore
import com.testtapyou.data.network.model.Point

class RoomDataStore(
    private val appContext: Context
) : PointsDataStore {

    private val database by lazy {
        Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "points"
        ).build()
    }

    override suspend fun getPoints(): List<Point> {
        val all = database.pointDao().getAll()
        return all.map { Point(it.x, it.y) }
    }

    override suspend fun deleteAll() {
        database.pointDao().deleteAll()
    }

    override suspend fun save(points: List<Point>) {
        val dbPoints = points.map { DBPoint(it.x, it.y) }
        database.pointDao().upsert(dbPoints)
    }
}