package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.liftingPerformance.LiftingPerformanceRepository
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import javax.inject.Inject

class EditLiftingPerformanceUseCase @Inject constructor(
    private val liftingPerformanceRepository: LiftingPerformanceRepository
) {
    suspend fun editLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        liftingPerformance: LiftingPerformance
    ) {
        liftingPerformanceRepository.editLiftingPerformance(
            userKey,
            farmKey,
            cowKey,
            liftingKey,
            liftingPerformance
        )
    }
}