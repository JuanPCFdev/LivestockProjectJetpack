package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.farm.FarmRepository
import com.example.livestockjetpackcompose.domain.model.Farm
import javax.inject.Inject

class GetUserFarmsUseCase @Inject constructor(
    private val farmRepository: FarmRepository
) {
    suspend fun getUserFarms(userKey: String, callback: (List<Farm>?, List<String>?) -> Unit) {
        farmRepository.getUserFarms(userKey, callback)
    }
}