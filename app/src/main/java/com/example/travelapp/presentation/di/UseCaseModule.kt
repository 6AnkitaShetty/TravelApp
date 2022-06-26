package com.example.travelapp.presentation.di

import com.example.travelapp.domain.repository.TrainStationRepository
import com.example.travelapp.domain.usecase.GetTrainStationsUseCase
import com.example.travelapp.domain.usecase.SearchTrainStationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideTrainStationsUseCases(trainStationRepository: TrainStationRepository): GetTrainStationsUseCase {
        return GetTrainStationsUseCase(trainStationRepository)
    }

    @Provides
    @Singleton
    fun provideSearchTrainStationsUseCases(trainStationRepository: TrainStationRepository): SearchTrainStationsUseCase {
        return SearchTrainStationsUseCase(trainStationRepository)
    }
}