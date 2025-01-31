package com.example.livestockjetpackcompose.data.repository.liftingPerformance

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LiftingPerformanceImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : LiftingPerformanceRepository {

    override suspend fun getSingleLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        callback: (LiftingPerformance?) -> Unit
    ) {
        firebaseDataSource.getSingleLiftingPerformance(
            userKey,
            farmKey,
            cowKey,
            liftingKey,
            callback
        )
    }

    override suspend fun registerNewLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingPerformance: LiftingPerformance
    ) {
        firebaseDataSource.registerNewLiftingPerformance(
            userKey,
            farmKey,
            cowKey,
            liftingPerformance
        )
    }

    override suspend fun deleteLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String
    ) {
        firebaseDataSource.deleteLiftingPerformance(userKey, farmKey, cowKey, liftingKey)
    }

    override suspend fun editLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        liftingPerformance: LiftingPerformance
    ) {
        firebaseDataSource.editLiftingPerformance(
            userKey,
            farmKey,
            cowKey,
            liftingKey,
            liftingPerformance
        )
    }

    override suspend fun getAllLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<LiftingPerformance>?, List<String>?) -> Unit
    ) {
        firebaseDataSource.getAllLiftingPerformance(userKey, farmKey, cowKey, callback)
    }

}