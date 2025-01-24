package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.cattle.CattleRepository
import com.example.livestockjetpackcompose.domain.model.Cattle
import javax.inject.Inject

class RegisterNewCowUseCase @Inject constructor(
    private val cattleRepository: CattleRepository
) {
    suspend fun registerNewCow(userKey: String, farmKey: String, cow: Cattle) {
        cattleRepository.registerNewCow(userKey, farmKey, cow)
    }
}