package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.liftingPerformance.LiftingPerformanceRepository
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import javax.inject.Inject

class GetSingleLiftingPerformanceUseCase @Inject constructor(
    private val liftingPerformanceRepository: LiftingPerformanceRepository
) {
    suspend fun getSingleLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        callback: (LiftingPerformance?) -> Unit
    ) {
        liftingPerformanceRepository.getSingleLiftingPerformance(
            userKey,
            farmKey,
            cowKey,
            liftingKey,
            callback
        )
    }
}