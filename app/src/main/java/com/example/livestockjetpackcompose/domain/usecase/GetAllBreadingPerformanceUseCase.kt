package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.breadingPerformance.BreadingPerformanceRepository
import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import javax.inject.Inject

class GetAllBreadingPerformanceUseCase @Inject constructor(
    private val breadingPerformanceRepository: BreadingPerformanceRepository
) {
    suspend fun getAllBreeadingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<BreedingPerformance>?, List<String>?) -> Unit
    ) {
        breadingPerformanceRepository.getAllBreeadingPerformance(userKey, farmKey, cowKey, callback)
    }
}