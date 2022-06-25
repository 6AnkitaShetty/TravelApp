package com.example.travelapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.travelapp.data.db.StationsEntity
import com.example.travelapp.data.model.ApiResponse
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.NetworkHelper
import com.example.travelapp.data.util.Resource
import com.example.travelapp.domain.repository.DataStoreRepository
import com.example.travelapp.domain.usecase.GetTrainStationsUseCase
import com.example.travelapp.domain.usecase.ReadSavedStationsUseCase
import com.example.travelapp.domain.usecase.SaveTrainStationsUseCase
import com.example.travelapp.domain.usecase.SearchTrainStationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TrainStationsViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val trainStationsUseCase: GetTrainStationsUseCase,
    private val searchTrainStationsUseCase: SearchTrainStationsUseCase,
    private val saveTrainStationsUseCase: SaveTrainStationsUseCase,
    private val readSavedStationsUseCase: ReadSavedStationsUseCase,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val trainStationsLiveData = MutableLiveData<Resource<List<Payload>>>()
    val trainStations: LiveData<Resource<List<Payload>>> = trainStationsLiveData
    var searchEnable: Boolean = false

    init {
        getTrainStations()
    }

    fun getTrainStations() {
        firstLaunch(true)
        viewModelScope.launch(Dispatchers.IO) {
            trainStationsLiveData.postValue(Resource.Loading())
            try {
                if (networkHelper.isNetworkConnected()) {
                    val stations = trainStationsUseCase.invoke()
                    offlineCacheTrainStations(stations.data!!)
                    trainStationsLiveData.postValue(stations)
                } else {
                    trainStationsLiveData.postValue(Resource.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                trainStationsLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }


    private fun offlineCacheTrainStations(data: List<Payload>) {
        viewModelScope.launch {
            val stationsEntity = StationsEntity(ApiResponse(data))
            saveTrainStationsUseCase.invoke(stationsEntity)
        }
    }

    fun readSavedTrainStations() {
        viewModelScope.launch {
            val savedStations = readSavedStationsUseCase.invoke()
            savedStations.collect {
                if (it.isNotEmpty()) {
                    it.first().apiResponse.payload
                        .let { payload ->
                            trainStationsLiveData.postValue(Resource.Success(payload))
                        }
                }
            }
        }
    }

    fun searchTrainStations(query: String) {
        if (query.isNotEmpty() && searchEnable) {
            viewModelScope.launch(Dispatchers.IO) {
                trainStationsLiveData.postValue(Resource.Loading())
                try {
                    if (networkHelper.isNetworkConnected()) {
                        val stations = searchTrainStationsUseCase.invoke(query)
                        trainStationsLiveData.postValue(stations)
                    } else {
                        trainStationsLiveData.postValue(Resource.Error("Internet is not available"))
                    }
                } catch (e: Exception) {
                    trainStationsLiveData.postValue(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    fun clearSearch() {
        searchEnable = false
    }

    fun enableSearch() {
        searchEnable = true
    }

    private fun firstLaunch(isFirstLaunch: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.putBoolean("FIRST_LAUNCH", isFirstLaunch)
        }
    }

    val isLaunched = runBlocking {
        dataStoreRepository.getBoolean("FIRST_LAUNCH") ?: false
    }
}