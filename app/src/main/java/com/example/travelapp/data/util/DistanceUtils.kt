package com.example.travelapp.data.util

import com.example.travelapp.data.model.Payload
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val theta = lon1 - lon2
    var dist = (sin(deg2rad(lat1))
            * sin(deg2rad(lat2))
            + (cos(deg2rad(lat1))
            * cos(deg2rad(lat2))
            * cos(deg2rad(theta))))
    dist = acos(dist)
    dist = rad2deg(dist)
    dist *= 60 * 1.1515
    return dist
}

private fun deg2rad(deg: Double): Double {
    return deg * Math.PI / 180.0
}

private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / Math.PI
}

fun sortPayload(payload: List<Payload>): List<Payload> {
    val payloadMap: MutableMap<Double, Payload> = mutableMapOf()
    payload.forEach {
        val distanceBetween =
            distance(
                Constants.CURRENT_LOCATION_LATITUDE,
                Constants.CURRENT_LOCATION_LONGITUDE, it.lat, it.lng
            )
        payloadMap[distanceBetween] = it
    }
    return payloadMap.toSortedMap().values.toList()
}

fun searchPayload(payload: List<Payload>, query: String): List<Payload> {
    return payload.filter {
        it.namen.lang.lowercase().contains(query.lowercase())
    }
}