package com.testtapyou.data.database.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PointDao {

    @Query("SELECT * FROM points")
    suspend fun getAll(): List<DBPoint>

    @Query("DELETE FROM points")
    suspend fun deleteAll()

    @Upsert
    suspend fun upsert(points: List<DBPoint>)
}