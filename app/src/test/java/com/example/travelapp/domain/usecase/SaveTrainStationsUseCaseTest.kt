package com.example.travelapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.travelapp.data.FakeDataUtil
import com.example.travelapp.data.db.StationsEntity
import com.example.travelapp.data.model.ApiResponse
import com.example.travelapp.domain.repository.FakeTrainStationRepository
import com.example.travelapp.util.MainCoroutineRule
import com.example.travelapp.util.runBlockingTest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SaveTrainStationsUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var fakeTrainStationRepository: FakeTrainStationRepository
    private lateinit var saveTrainStationsUseCase: SaveTrainStationsUseCase

    @Before
    fun setUp() {
        fakeTrainStationRepository = FakeTrainStationRepository()
        saveTrainStationsUseCase = SaveTrainStationsUseCase(fakeTrainStationRepository)
    }

    @Test
    fun testSaveTrainStationsUseCase_ReturnResult() {
        coroutineRule.runBlockingTest {
            val stations = FakeDataUtil.getFakeTrainStations()
            val result =
                fakeTrainStationRepository.saveTrainStations(StationsEntity(ApiResponse(stations)))
            TestCase.assertEquals(result, 0)
        }
    }

}