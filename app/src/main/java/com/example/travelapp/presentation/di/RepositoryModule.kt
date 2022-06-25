package com.example.travelapp.presentation.di

import android.content.Context
import com.example.travelapp.data.api.TrainStationApiService
import com.example.travelapp.data.db.StationsDao
import com.example.travelapp.data.repository.DataStoreRepositoryImpl
import com.example.travelapp.data.repository.TrainStationRepositoryImpl
import com.example.travelapp.domain.repository.DataStoreRepository
import com.example.travelapp.domain.repository.TrainStationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTrainStationRepository(api: TrainStationApiService,dao:StationsDao): TrainStationRepository {
        return TrainStationRepositoryImpl(api,dao)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext app:Context):DataStoreRepository = DataStoreRepositoryImpl(app)
}