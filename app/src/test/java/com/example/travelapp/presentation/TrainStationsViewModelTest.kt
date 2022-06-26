package com.example.travelapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.travelapp.data.FakeDataUtil
import com.example.travelapp.data.util.NetworkHelper
import com.example.travelapp.data.util.Resource
import com.example.travelapp.domain.repository.DataStoreRepository
import com.example.travelapp.domain.repository.FakeDataStoreRepository
import com.example.travelapp.domain.repository.FakeTrainStationRepository
import com.example.travelapp.domain.usecase.GetTrainStationsUseCase
import com.example.travelapp.domain.usecase.ReadSavedStationsUseCase
import com.example.travelapp.domain.usecase.SaveTrainStationsUseCase
import com.example.travelapp.domain.usecase.SearchTrainStationsUseCase
import com.example.travelapp.presentation.viewmodel.TrainStationsViewModel
import com.example.travelapp.util.MainCoroutineRule
import com.example.travelapp.util.provideFakeCoroutinesDispatcherProvider
import com.example.travelapp.util.runBlockingTest
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TrainStationsViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var getTrainStationsUseCase: GetTrainStationsUseCase
    private lateinit var searchTrainStationsUseCase: SearchTrainStationsUseCase
    private lateinit var saveTrainStationsUseCase: SaveTrainStationsUseCase
    private lateinit var readSavedStationsUseCase: ReadSavedStationsUseCase
    private lateinit var viewModel: TrainStationsViewModel
    private val testDispatcher = coroutineRule.testDispatcher

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var trainStationRepository: FakeTrainStationRepository

    @Mock
    private lateinit var dataStoreRepository: FakeDataStoreRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getTrainStationsUseCase = GetTrainStationsUseCase(trainStationRepository)
        searchTrainStationsUseCase = SearchTrainStationsUseCase(trainStationRepository)
        saveTrainStationsUseCase = SaveTrainStationsUseCase(trainStationRepository)
        readSavedStationsUseCase = ReadSavedStationsUseCase((trainStationRepository))
        viewModel = TrainStationsViewModel(
            networkHelper = networkHelper,
            getTrainStationsUseCase,
            searchTrainStationsUseCase,
            saveTrainStationsUseCase,
            readSavedStationsUseCase,
            dataStoreRepository,
            coroutinesDispatcherProvider = provideFakeCoroutinesDispatcherProvider(testDispatcher)
        )
    }

    @Test
    fun `when calling for results then return results`() {
        coroutineRule.runBlockingTest {
            whenever(networkHelper.isNetworkConnected())
                .thenReturn(true)
            // Stub repository with fake stations
            whenever(trainStationRepository.getTrainStations())
                .thenAnswer { (FakeDataUtil.getFakeTrainStationsResponse()) }

            //When
            viewModel.getTrainStations()

            //then
            assertThat(viewModel.trainStations.value).isNotNull()
            val stations = viewModel.trainStations.value?.data

            assertThat(stations?.isNotEmpty())

            // compare the response with fake list
            assertThat(stations).hasSize(FakeDataUtil.getFakeTrainStations().size)

            // compare the data and also order
            assertThat(stations).containsExactlyElementsIn(
                FakeDataUtil.getFakeTrainStations()
            ).inOrder()
        }
    }

    @Test
    fun `when calling for results then return error`() {
        coroutineRule.runBlockingTest {
            whenever(networkHelper.isNetworkConnected())
                .thenReturn(true)
            // Stub repository with fake favorites
            whenever(trainStationRepository.getTrainStations())
                .thenAnswer { Resource.Error("Error occurred", null) }

            //When
            viewModel.getTrainStations()

            //then
            val response = viewModel.trainStations.value
            assertThat(response?.message).isNotNull()
            assertThat(response?.message).isEqualTo("Error occurred")
        }
    }

}