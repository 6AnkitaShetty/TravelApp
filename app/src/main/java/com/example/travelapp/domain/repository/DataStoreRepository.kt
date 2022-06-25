package com.example.travelapp.domain.repository

interface DataStoreRepository {
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
}