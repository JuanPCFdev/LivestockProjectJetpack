package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.vaccine.VaccineRepository
import javax.inject.Inject

class DeleteVaccineUseCase @Inject constructor(
    private val vaccineRepository: VaccineRepository
) {
    suspend fun deleteVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String
    ) {
        vaccineRepository.deleteVaccine(userKey, farmKey, cowKey, vaccineKey)
    }
}