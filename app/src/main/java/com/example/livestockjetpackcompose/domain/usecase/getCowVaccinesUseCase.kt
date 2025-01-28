package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.vaccine.VaccineRepository
import com.example.livestockjetpackcompose.domain.model.Vaccine
import javax.inject.Inject

class getCowVaccinesUseCase @Inject constructor(
    private val vaccineRepository: VaccineRepository
) {
    suspend fun getCowVaccines(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Vaccine>?, List<String>?) -> Unit
    ) {
        vaccineRepository.getCowVaccines(userKey, farmKey, cowKey, callback)
    }
}