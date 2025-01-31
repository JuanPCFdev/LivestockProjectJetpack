package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.liftingPerformance.LiftingPerformanceRepository
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import javax.inject.Inject

class GetAllLiftingPerformanceUseCase @Inject constructor(
    private val liftingPerformanceRepository: LiftingPerformanceRepository
) {
    suspend fun getAllLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<LiftingPerformance>?, List<String>?) -> Unit
    ) {
        liftingPerformanceRepository.getAllLiftingPerformance(userKey, farmKey, cowKey, callback)
    }
}