package com.example.travelapp.domain.repository

import com.example.travelapp.data.FakeDataUtil
import com.example.travelapp.data.db.StationsEntity
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTrainStationRepository : TrainStationRepository {
    private val stations = mutableListOf<StationsEntity>()
    override suspend fun getTrainStations(): Resource<List<Payload>> {
       return Resource.Success(FakeDataUtil.getFakeTrainStations())
    }

    override suspend fun searchTrainStations(query: String): Resource<List<Payload>> {
        return Resource.Success(FakeDataUtil.getFakeSearchTrainStations(query))
    }

    override suspend fun saveTrainStations(stationsEntity: StationsEntity) :Long{
        stations.add(stationsEntity)
        return 0
    }

    override fun getSavedTrainStations(): Flow<List<StationsEntity>> {
        return flow {
            emit(stations)
        }
    }
}