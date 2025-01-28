package com.example.livestockjetpackcompose.data.repository.vaccine

import com.example.livestockjetpackcompose.domain.model.Vaccine

interface VaccineRepository {
    suspend fun getCowVaccines(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Vaccine>?, List<String>?) -> Unit
    )

    suspend fun registerNewVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccine: Vaccine
    )

    suspend fun deleteVaccine(userKey: String, farmKey: String, cowKey: String, vaccineKey: String)

    suspend fun editVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        vaccine: Vaccine
    )

    suspend fun getSingleVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        callback: (Vaccine?) -> Unit
    )

}