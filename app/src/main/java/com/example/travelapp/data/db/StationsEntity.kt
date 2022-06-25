package com.example.travelapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travelapp.data.model.ApiResponse

@Entity(tableName = "stations_table")
class StationsEntity(
    var apiResponse: ApiResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}