package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.cattle.CattleRepository
import com.example.livestockjetpackcompose.domain.model.Cattle
import javax.inject.Inject

class GetCowDataUseCase @Inject constructor(
    private val cattleRepository: CattleRepository
) {
    suspend fun getCowData(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (Cattle?) -> Unit
    ) {
        cattleRepository.getCowData(userKey, farmKey, cowKey, callback)
    }
}