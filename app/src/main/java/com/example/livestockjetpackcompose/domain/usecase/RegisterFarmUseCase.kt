package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.farm.FarmRepository
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.model.User
import javax.inject.Inject

class RegisterFarmUseCase @Inject constructor(
    private val farmRepository: FarmRepository
) {
    suspend operator fun invoke(userKey: String, farm: Farm) {
        farmRepository.registerNewFarm(userKey, farm)
    }
}