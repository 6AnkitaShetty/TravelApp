package com.example.travelapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.travelapp.util.MainCoroutineRule
import com.example.travelapp.domain.repository.FakeTrainStationRepository
import com.example.travelapp.util.runBlockingTest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetTrainStationsUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var fakeTrainStationRepository: FakeTrainStationRepository
    private lateinit var getTrainStationsUseCase: GetTrainStationsUseCase

    @Before
    fun setUp() {
        fakeTrainStationRepository = FakeTrainStationRepository()
        getTrainStationsUseCase = GetTrainStationsUseCase(fakeTrainStationRepository)
    }

    @Test
    fun getTrainStations_Returns_Success() {
        coroutineRule.runBlockingTest {
            val stations = getTrainStationsUseCase.invoke()
            TestCase.assertNotNull(stations)
            TestCase.assertEquals(stations.data?.size, 2)
        }
    }
}