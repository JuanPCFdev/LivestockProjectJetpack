package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.insemination.InseminationRepository
import com.example.livestockjetpackcompose.domain.model.Insemination
import javax.inject.Inject

class RegisterNewInseminationUseCase @Inject constructor(
    private val inseminationRepository: InseminationRepository
) {
    suspend fun registerNewInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        insemination: Insemination
    ) {
        inseminationRepository.registerNewInsemination(userKey, farmKey, cowKey, insemination)
    }
}