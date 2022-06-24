package com.example.travelapp.data.model

data class Payload(
    val EVACode: String,
    val UICCode: String,
    val code: String,
    val heeftFaciliteiten: Boolean,
    val heeftReisassistentie: Boolean,
    val heeftVertrektijden: Boolean,
    val ingangsDatum: String,
    val land: String,
    val lat: Double,
    val lng: Double,
    val naderenRadius: Int,
    val namen: Namen,
    val nearbyMeLocationId: NearbyMeLocationId,
    val radius: Int,
    val sporen: List<Sporen>,
    val stationType: String,
    val synoniemen: List<String>
)