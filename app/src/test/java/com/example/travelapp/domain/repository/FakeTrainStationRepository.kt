package com.example.travelapp.domain.repository

import com.example.travelapp.data.FakeDataUtil
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.Resource

class FakeTrainStationRepository : TrainStationRepository {

    override suspend fun getTrainStations(): Resource<List<Payload>> {
       return Resource.Success(FakeDataUtil.getFakeTrainStations())
    }

    override suspend fun searchTrainStations(query: String): Resource<List<Payload>> {
        return Resource.Success(FakeDataUtil.getFakeSearchTrainStations(query))
    }
}