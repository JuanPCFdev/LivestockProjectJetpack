package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.cattle.CattleRepository
import com.example.livestockjetpackcompose.domain.model.Cattle
import javax.inject.Inject

class EditCowUseCase @Inject constructor(
    private val cattleRepository: CattleRepository
) {
    suspend fun editCow(cow: Cattle, userKey: String, farmKey: String, cowKey: String) {
        cattleRepository.editCow(cow, userKey, farmKey, cowKey)
    }
}