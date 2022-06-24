package com.example.travelapp.domain.usecase

import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.Resource
import com.example.travelapp.domain.repository.TrainStationRepository

class SearchTrainStationsUseCase(
    private val trainStationRepository: TrainStationRepository
) {
    suspend operator fun invoke(query:String): Resource<List<Payload>> {
        return trainStationRepository.searchTrainStations(query)
    }
}