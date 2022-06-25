package com.example.travelapp.domain.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.travelapp.data.FakeDataUtil
import com.example.travelapp.data.api.TrainStationApiService
import com.example.travelapp.data.db.StationsDao
import com.example.travelapp.data.db.StationsDatabase
import com.example.travelapp.data.db.StationsEntity
import com.example.travelapp.data.model.ApiResponse
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.repository.TrainStationRepositoryImpl
import com.example.travelapp.data.util.Resource
import com.example.travelapp.util.MainCoroutineRule
import com.example.travelapp.util.MockWebServerBaseTest
import com.example.travelapp.util.runBlockingTest
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
    private lateinit var stationsDao: StationsDao
    private lateinit var stationsDatabase: StationsDatabase
    private lateinit var responseObserver: Observer<List<StationsEntity>>
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        stationsDatabase = Room.inMemoryDatabaseBuilder(
            context, StationsDatabase::class.java
        ).allowMainThreadQueries().build()
        stationsDao = stationsDatabase.getStationsDao()
        trainStationApiService = provideTestApiService()
        trainStationRepositoryImpl = TrainStationRepositoryImpl(trainStationApiService,stationsDao)
        responseObserver = Observer { }
    }

    @Test
    fun testFavoriteNewsInsertionInDb() {
        coroutineRule.runBlockingTest {
            trainStationRepositoryImpl.saveTrainStations(StationsEntity(ApiResponse(FakeDataUtil.getFakeTrainStations())) )
            val stations = trainStationRepositoryImpl.getSavedTrainStations().asLiveData()
            stations.observeForever(responseObserver)
            assertThat(stations.value?.isNotEmpty()).isTrue()
        }
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