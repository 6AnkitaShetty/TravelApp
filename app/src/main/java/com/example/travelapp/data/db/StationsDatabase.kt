package com.example.travelapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [StationsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StationsConverters::class)
abstract class StationsDatabase : RoomDatabase() {
    abstract fun getStationsDao(): StationsDao
}