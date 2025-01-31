package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.insemination.InseminationRepository
import javax.inject.Inject

class DeleteInseminationUseCase @Inject constructor(
    private val inseminationRepository: InseminationRepository
) {
    suspend fun deleteInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        inseminationKey: String
    ) {
        inseminationRepository.deleteInsemination(userKey, farmKey, cowKey, inseminationKey)
    }
}