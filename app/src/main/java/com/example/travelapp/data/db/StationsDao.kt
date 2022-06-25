package com.example.travelapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(stationsEntity: StationsEntity): Long

    @Query("SELECT * FROM stations_table")
    fun getTrainStations(): Flow<List<StationsEntity>>
}