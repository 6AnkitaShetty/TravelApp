package com.example.travelapp.presentation.di

import com.example.travelapp.data.api.TrainStationApiService
import com.example.travelapp.data.repository.TrainStationRepositoryImpl
import com.example.travelapp.domain.repository.TrainStationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTrainStationRepository(api: TrainStationApiService): TrainStationRepository {
        return TrainStationRepositoryImpl(api)
    }
}