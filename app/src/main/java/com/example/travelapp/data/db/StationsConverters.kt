package com.example.travelapp.data.db

import androidx.room.TypeConverter
import com.example.travelapp.data.model.ApiResponse
import com.example.travelapp.data.model.Payload
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StationsConverters {
    var gson = Gson()

    @TypeConverter
    fun apiResponseToString(apiResponse: ApiResponse): String {
        return gson.toJson(apiResponse)
    }

    @TypeConverter
    fun stringToApiResponse(data: String): ApiResponse {
        val listType = object : TypeToken<ApiResponse>() {}.type
        return gson.fromJson(data, listType)
    }

}