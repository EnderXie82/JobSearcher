package com.enderx.jobsearcher.core.datastore

interface DataStoreRepository {
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
}