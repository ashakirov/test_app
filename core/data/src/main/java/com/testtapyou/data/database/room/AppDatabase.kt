package com.testtapyou.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBPoint::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pointDao(): PointDao
}