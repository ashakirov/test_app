package com.testtapyou.data.di

import android.content.Context
import com.testtapyou.data.database.PointsDataStore
import com.testtapyou.data.database.room.RoomDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePointsDataStore(
        @ApplicationContext appContext: Context
    ): PointsDataStore {
        return RoomDataStore(appContext)
    }
}