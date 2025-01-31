package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.insemination.InseminationRepository
import com.example.livestockjetpackcompose.domain.model.Insemination
import javax.inject.Inject

class GetAllInseminationUseCase @Inject constructor(
    private val inseminationRepository: InseminationRepository
) {
    suspend fun getAllInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Insemination>?, List<String>?) -> Unit
    ) {
        inseminationRepository.getAllInsemination(userKey, farmKey, cowKey, callback)
    }
}