package com.example.travelapp.data

import com.example.travelapp.data.model.Namen
import com.example.travelapp.data.model.NearbyMeLocationId
import com.example.travelapp.data.model.Payload
import com.example.travelapp.data.util.Resource
import com.example.travelapp.data.util.searchPayload
import com.example.travelapp.data.util.sortPayload

object FakeDataUtil {

    fun getFakeTrainStationsResponse(): Resource<List<Payload>> {
        val trainStations = sortPayload(getFakeTrainStations().toList())
        return Resource.Success(trainStations)
    }

    fun getFakeTrainStations(): MutableList<Payload> {
        val stationsList: MutableList<Payload> = arrayListOf()
        val payload1 = Payload(
            UICCode = "8301307",
            stationType = "INTERCITY_STATION",
            EVACode = "8300056",
            code = "COMO",
            sporen = listOf(),
            synoniemen = listOf(),
            heeftFaciliteiten = true,
            heeftVertrektijden = true,
            heeftReisassistentie = false,
            namen = Namen(
                lang = "Como S. Giovanni",
                middel = "Como S. Giovanni",
                kort = "Como S. G"
            ),
            land = "I",
            lat = 45.80889,
            lng = 9.071944,
            radius = 0,
            naderenRadius = 0,
            ingangsDatum = "2018-12-16",
            nearbyMeLocationId = NearbyMeLocationId(value = "COMO", type = "stationV2")
        )
        val payload2 = Payload(
            UICCode = "8301645",
            stationType = "INTERCITY_STATION",
            EVACode = "8300047",
            code = "DOMOD",
            sporen = listOf(),
            synoniemen = listOf(),
            heeftFaciliteiten = true,
            heeftVertrektijden = true,
            heeftReisassistentie = false,
            namen = Namen(
                lang = "Milano Porta Garibaldi",
                middel = "Milano Garibaldi",
                kort = "Milano G"
            ),
            land = "I",
            lat = 45.484722,
            lng = 9.187778,
            radius = 0,
            naderenRadius = 0,
            ingangsDatum = "2022-05-28",
            nearbyMeLocationId = NearbyMeLocationId(value = "DOMOD", type = "stationV2")
        )
        stationsList.add(payload1)
        stationsList.add(payload2)
        return stationsList
    }

    fun getFakeSearchTrainStations(query: String): MutableList<Payload> {
        val searchedStations = searchPayload(getFakeTrainStations(), query)
        return searchedStations.toMutableList()
    }
}