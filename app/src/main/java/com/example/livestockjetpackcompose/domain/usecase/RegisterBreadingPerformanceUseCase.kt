package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.breadingPerformance.BreadingPerformanceRepository
import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import javax.inject.Inject

class RegisterBreadingPerformanceUseCase @Inject constructor(
    private val breadingPerformanceRepository: BreadingPerformanceRepository
) {
    suspend fun registerBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        breeadingPerformance: BreedingPerformance
    ) {
        breadingPerformanceRepository.registerBreeadingPerformance(
            userKey,
            farmKey,
            cowKey,
            breeadingPerformance
        )
    }
}