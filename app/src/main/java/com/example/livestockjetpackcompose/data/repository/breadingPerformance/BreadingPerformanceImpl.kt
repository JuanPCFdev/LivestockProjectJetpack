package com.example.livestockjetpackcompose.data.repository.breadingPerformance

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreadingPerformanceImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : BreadingPerformanceRepository {

    override suspend fun registerBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        breeadingPerformance: BreedingPerformance
    ) {
        firebaseDataSource.registerBreeadingPerformance(
            userKey,
            farmKey,
            cowKey,
            breeadingPerformance
        )
    }

    override suspend fun deleteBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        breeadingPerformanceKey: String
    ) {
        firebaseDataSource.deleteBreeadingPerformance(
            userKey,
            farmKey,
            cowKey,
            breeadingPerformanceKey
        )
    }

    override suspend fun getAllBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<BreedingPerformance>?, List<String>?) -> Unit
    ) {
        firebaseDataSource.getAllBreeadingPerformance(userKey, farmKey, cowKey, callback)
    }

}