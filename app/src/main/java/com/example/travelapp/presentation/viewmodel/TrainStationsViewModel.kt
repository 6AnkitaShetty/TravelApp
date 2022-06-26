package com.example.travelapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.NetworkHelper
import com.example.travelapp.data.util.Resource
import com.example.travelapp.domain.usecase.GetTrainStationsUseCase
import com.example.travelapp.domain.usecase.SearchTrainStationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TrainStationsViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val trainStationsUseCase: GetTrainStationsUseCase,
    private val searchTrainStationsUseCase: SearchTrainStationsUseCase,
) : ViewModel() {

    init {
        getTrainStations()
    }

    private val trainStationsLiveData = MutableLiveData<Resource<List<Payload>>>()
    val trainStations: LiveData<Resource<List<Payload>>> = trainStationsLiveData
    private var searchEnable: Boolean = false

    fun getTrainStations() {
        viewModelScope.launch(Dispatchers.IO) {
            trainStationsLiveData.postValue(Resource.Loading())
            try {
                if (networkHelper.isNetworkConnected()) {
                    val stations = trainStationsUseCase.invoke()
                    trainStationsLiveData.postValue(stations)
                } else {
                    trainStationsLiveData.postValue(Resource.Error("Internet is not available"))
                }
            } catch (e: Exception) {
                trainStationsLiveData.postValue(Resource.Error(e.message.toString()))
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
}