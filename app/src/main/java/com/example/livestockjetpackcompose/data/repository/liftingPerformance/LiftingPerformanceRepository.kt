package com.example.livestockjetpackcompose.data.repository.liftingPerformance

import com.example.livestockjetpackcompose.domain.model.LiftingPerformance

interface LiftingPerformanceRepository {

    suspend fun getSingleLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        callback: (LiftingPerformance?) -> Unit
    )

    suspend fun registerNewLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingPerformance: LiftingPerformance
    )

    suspend fun deleteLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String
    )

    suspend fun editLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        liftingPerformance: LiftingPerformance
    )

    suspend fun getAllLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<LiftingPerformance>?, List<String>?) -> Unit
    )

}