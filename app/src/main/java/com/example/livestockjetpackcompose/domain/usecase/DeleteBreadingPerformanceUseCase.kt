package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.breadingPerformance.BreadingPerformanceRepository
import javax.inject.Inject

class DeleteBreadingPerformanceUseCase @Inject constructor(
    private val breadingPerformanceRepository: BreadingPerformanceRepository
) {
    suspend fun deleteBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        breeadingPerformanceKey: String
    ) {
        breadingPerformanceRepository.deleteBreeadingPerformance(
            userKey,
            farmKey,
            cowKey,
            breeadingPerformanceKey
        )
    }
}