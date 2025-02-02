package com.example.livestockjetpackcompose.data.repository.breadingPerformance

import com.example.livestockjetpackcompose.domain.model.BreedingPerformance

interface BreadingPerformanceRepository {
    suspend fun registerBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        breeadingPerformance: BreedingPerformance
    )

    suspend fun deleteBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        breeadingPerformanceKey: String
    )

    suspend fun getAllBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<BreedingPerformance>?, List<String>?) -> Unit
    )
}