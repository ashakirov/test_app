package com.testtapyou.data.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class DBPoint(
    @ColumnInfo(name = "x")
    val x: Float,
    @ColumnInfo(name = "y")
    val y: Float
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}