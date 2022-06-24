package com.example.travelapp.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.travelapp.data.api.TrainStationApiService
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.repository.TrainStationRepositoryImpl
import com.example.travelapp.data.util.Resource
import com.example.travelapp.util.MainCoroutineRule
import com.example.travelapp.util.MockWebServerBaseTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@Config(sdk = [30])
@RunWith(
    RobolectricTestRunner::class
)
class TrainStationRepositoryImplTest : MockWebServerBaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    override fun isMockServerEnabled(): Boolean = true
    private lateinit var trainStationRepositoryImpl: TrainStationRepositoryImpl
    private lateinit var trainStationApiService: TrainStationApiService

    @Before
    fun setup() {
        trainStationApiService = provideTestApiService()
        trainStationRepositoryImpl = TrainStationRepositoryImpl(trainStationApiService)
    }

    @Test
    fun `given response ok when fetching results then return a list with elements`() {
        runBlocking {
            mockHttpResponse("train_station_response.json", HttpURLConnection.HTTP_OK)
            val apiResponse = trainStationRepositoryImpl.getTrainStations()

            assertThat(apiResponse).isNotNull()
            assertThat(apiResponse.data).hasSize(7)
        }
    }

    @Test
    fun `given response failure when fetching results then return exception`() {
        runBlocking {
            mockHttpResponse(502)
            val apiResponse = trainStationRepositoryImpl.getTrainStations()

            Assert.assertNotNull(apiResponse)
            val expectedValue = Resource.Error("Server Error", null)
            assertThat(expectedValue.message).isEqualTo(apiResponse.message)
        }
    }
}