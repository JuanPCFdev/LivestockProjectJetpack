package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.cattle.CattleRepository
import javax.inject.Inject


class DeleteCowUseCase @Inject constructor(
    private val cattleRepository: CattleRepository
) {
    suspend fun deleteCow(userKey: String, farmKey: String, cowKey: String) {
        cattleRepository.deleteCow(userKey, farmKey, cowKey)
    }
}