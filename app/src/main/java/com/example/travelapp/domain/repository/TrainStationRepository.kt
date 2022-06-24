package com.example.travelapp.domain.repository

import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.Resource

interface TrainStationRepository {
    suspend fun getTrainStations(): Resource<List<Payload>>
    suspend fun searchTrainStations(query: String): Resource<List<Payload>>
}