package com.example.travelapp.data.api

import com.example.travelapp.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface TrainStationApiService {
    @GET("stations")
    suspend fun getTrainStations(): Response<ApiResponse>
}