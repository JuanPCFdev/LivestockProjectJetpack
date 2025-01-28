package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.vaccine.VaccineRepository
import com.example.livestockjetpackcompose.domain.model.Vaccine
import javax.inject.Inject

class EditVaccineUseCase @Inject constructor(
    private val vaccineRepository: VaccineRepository
) {
    suspend fun editVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        vaccine: Vaccine
    ) {
        vaccineRepository.editVaccine(userKey, farmKey, cowKey, vaccineKey, vaccine)
    }
}