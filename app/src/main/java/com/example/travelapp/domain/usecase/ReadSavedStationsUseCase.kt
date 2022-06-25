package com.example.travelapp.domain.usecase

import com.example.travelapp.data.db.StationsEntity
import com.example.travelapp.domain.repository.TrainStationRepository
import kotlinx.coroutines.flow.Flow

class ReadSavedStationsUseCase(
    private val trainStationRepository: TrainStationRepository
) {
    operator fun invoke(): Flow<List<StationsEntity>> {
        return trainStationRepository.getSavedTrainStations()
    }
}