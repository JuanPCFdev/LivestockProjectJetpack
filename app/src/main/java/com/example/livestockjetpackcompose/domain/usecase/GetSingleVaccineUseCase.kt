package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.vaccine.VaccineRepository
import com.example.livestockjetpackcompose.domain.model.Vaccine
import javax.inject.Inject

class GetSingleVaccineUseCase @Inject constructor(
    private val vaccineRepository: VaccineRepository
) {
    suspend fun getSingleVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        callback: (Vaccine?) -> Unit
    ) {
        vaccineRepository.getSingleVaccine(userKey, farmKey, cowKey, vaccineKey, callback)
    }
}