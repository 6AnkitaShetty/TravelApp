package com.example.travelapp.domain.usecase


import com.example.travelapp.data.db.StationsEntity
import com.example.travelapp.domain.repository.TrainStationRepository

class SaveTrainStationsUseCase(
    private val trainStationRepository: TrainStationRepository
) {
    suspend operator fun invoke(stationsEntity: StationsEntity): Long {
        return trainStationRepository.saveTrainStations(stationsEntity)
    }
}