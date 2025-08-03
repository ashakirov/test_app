package com.testtapyou.data.di

import com.testtapyou.data.database.PointsDataStore
import com.testtapyou.data.network.NetworkDataSource
import com.testtapyou.data.repository.PointsRepository
import com.testtapyou.data.repository.PointsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    @ViewModelScoped
    fun providePointsRepository(
        remoteDataSource: NetworkDataSource,
        localDataStore: PointsDataStore
    ): PointsRepository {
        return PointsRepositoryImpl(remoteDataSource, localDataStore)
    }
}