package com.example.travelapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
class SearchTrainStationsUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var fakeTrainStationRepository: FakeTrainStationRepository
    private lateinit var searchTrainStationsUseCase: SearchTrainStationsUseCase

    @Before
    fun setUp() {
        fakeTrainStationRepository = FakeTrainStationRepository()
        searchTrainStationsUseCase = SearchTrainStationsUseCase(fakeTrainStationRepository)
    }

    @Test
    fun searchTrainStations_Returns_Success() {
        coroutineRule.runBlockingTest {
            val stations = searchTrainStationsUseCase.invoke("Milano")
            TestCase.assertNotNull(stations)
            TestCase.assertEquals(stations.data?.size, 1)
        }
    }
}