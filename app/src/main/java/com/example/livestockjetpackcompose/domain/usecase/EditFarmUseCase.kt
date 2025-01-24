package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.farm.FarmRepository
import com.example.livestockjetpackcompose.domain.model.Farm
import javax.inject.Inject

class EditFarmUseCase @Inject constructor(
    private val farmRepository: FarmRepository
) {
    suspend fun editFarm(farm: Farm, userKey: String, farmKey: String) {
        farmRepository.editFarm(farm, userKey, farmKey)
    }
}