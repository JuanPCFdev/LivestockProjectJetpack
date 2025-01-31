package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.liftingPerformance.LiftingPerformanceRepository
import javax.inject.Inject

class DeleteLiftingPerformanceUseCase @Inject constructor(
    private val liftingPerformanceRepository: LiftingPerformanceRepository
) {
    suspend fun deleteLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String
    ) {
        liftingPerformanceRepository.deleteLiftingPerformance(userKey, farmKey, cowKey, liftingKey)
    }
}