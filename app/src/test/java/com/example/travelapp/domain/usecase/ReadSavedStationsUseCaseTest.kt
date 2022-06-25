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
import kotlinx.coroutines.flow.count
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ReadSavedStationsUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var fakeTrainStationRepository: FakeTrainStationRepository
    private lateinit var readSavedStationsUseCase: ReadSavedStationsUseCase

    @Before
    fun setUp() {
        fakeTrainStationRepository = FakeTrainStationRepository()
        readSavedStationsUseCase = ReadSavedStationsUseCase(fakeTrainStationRepository)
    }

    @Test
    fun testReadSavedStationsUseCase_ReturnResult() {
        coroutineRule.runBlockingTest {
            val result =
                fakeTrainStationRepository.getSavedTrainStations()
            TestCase.assertEquals(result.count(), 1)
        }
    }

}