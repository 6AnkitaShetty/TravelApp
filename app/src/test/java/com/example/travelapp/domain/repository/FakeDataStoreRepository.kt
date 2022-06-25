package com.example.travelapp.domain.repository

class FakeDataStoreRepository : DataStoreRepository {
    override suspend fun putBoolean(key: String, value: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getBoolean(key: String): Boolean? {
        TODO("Not yet implemented")
    }
}