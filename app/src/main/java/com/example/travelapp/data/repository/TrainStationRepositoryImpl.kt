package com.example.travelapp.data.repository

import com.example.travelapp.data.api.TrainStationApiService
import com.example.travelapp.data.model.ApiResponse
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.Resource
import com.example.travelapp.data.util.searchPayload
import com.example.travelapp.data.util.sortPayload
import com.example.travelapp.domain.repository.TrainStationRepository
import retrofit2.Response

class TrainStationRepositoryImpl(private val trainStationApiService: TrainStationApiService) :
    TrainStationRepository {

    override suspend fun getTrainStations(): Resource<List<Payload>> {
        return responseToResource(trainStationApiService.getTrainStations(), null)
    }

    override suspend fun searchTrainStations(query: String): Resource<List<Payload>> {
        return responseToResource(trainStationApiService.getTrainStations(), query)
    }

    private fun responseToResource(
        response: Response<ApiResponse>,
        query: String?
    ): Resource<List<Payload>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                val sortedPayloadList = sortPayload(result.payload)
                if (query == null) {
                    return Resource.Success(sortedPayloadList)
                } else {
                    val searchPayloadList = searchPayload(result.payload, query)
                    return Resource.Success(searchPayloadList)
                }
            }
        }
        return Resource.Error(response.message())
    }
}