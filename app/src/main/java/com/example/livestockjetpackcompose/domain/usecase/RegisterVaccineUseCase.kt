package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.vaccine.VaccineRepository
import com.example.livestockjetpackcompose.domain.model.Vaccine
import javax.inject.Inject

class RegisterVaccineUseCase @Inject constructor(
    private val vaccineRepository: VaccineRepository
) {
    suspend fun registerNewVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccine: Vaccine
    ) {
        vaccineRepository.registerNewVaccine(userKey, farmKey, cowKey, vaccine)
    }
}