package com.example.travelapp.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.travelapp.data.db.StationsDao
import com.example.travelapp.data.db.StationsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideStationsDatabase(app: Application): StationsDatabase {
        return Room.databaseBuilder(
            app,
            StationsDatabase::class.java,
            "stations_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideStationsDao(stationsDatabase: StationsDatabase): StationsDao {
        return stationsDatabase.getStationsDao()
    }
}