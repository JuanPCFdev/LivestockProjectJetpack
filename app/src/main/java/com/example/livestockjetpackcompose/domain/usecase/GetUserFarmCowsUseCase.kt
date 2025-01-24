package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.cattle.CattleRepository
import com.example.livestockjetpackcompose.domain.model.Cattle
import javax.inject.Inject

class GetUserFarmCowsUseCase @Inject constructor(
    private val cattleRepository: CattleRepository
) {

    suspend fun getUserFarmCows(
        userKey: String,
        farmKey: String,
        callback: (List<Cattle>?, List<String>?) -> Unit
    ) {
        cattleRepository.getUserFarmCows(userKey, farmKey, callback)
    }

}