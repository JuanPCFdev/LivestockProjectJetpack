package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.farm.FarmRepository
import javax.inject.Inject

class DeleteFarmUseCase @Inject constructor(
    private val farmRepository: FarmRepository
) {
    suspend fun deleteFarm(userKey: String, farmKey: String) {
        farmRepository.deleteFarm(userKey, farmKey)
    }
}