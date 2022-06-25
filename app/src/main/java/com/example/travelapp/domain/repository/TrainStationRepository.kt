package com.example.travelapp.domain.repository

import com.example.travelapp.data.db.StationsEntity
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface TrainStationRepository {
    suspend fun getTrainStations(): Resource<List<Payload>>
    suspend fun searchTrainStations(query: String): Resource<List<Payload>>
    suspend fun saveTrainStations(stationsEntity: StationsEntity): Long
    fun getSavedTrainStations(): Flow<List<StationsEntity>>
}