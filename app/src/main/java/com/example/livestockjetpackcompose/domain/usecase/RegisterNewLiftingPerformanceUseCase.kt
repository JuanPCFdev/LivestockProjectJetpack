package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.liftingPerformance.LiftingPerformanceRepository
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import javax.inject.Inject

class RegisterNewLiftingPerformanceUseCase @Inject constructor(
    private val liftingPerformanceRepository: LiftingPerformanceRepository
) {
    suspend fun registerNewLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingPerformance: LiftingPerformance
    ) {
        liftingPerformanceRepository.registerNewLiftingPerformance(
            userKey,
            farmKey,
            cowKey,
            liftingPerformance
        )
    }
}