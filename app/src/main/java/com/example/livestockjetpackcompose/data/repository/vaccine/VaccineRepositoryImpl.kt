package com.example.livestockjetpackcompose.data.repository.vaccine

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.domain.model.Vaccine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VaccineRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : VaccineRepository {

    override suspend fun getCowVaccines(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Vaccine>?, List<String>?) -> Unit
    ) {
        firebaseDataSource.getCowVaccines(userKey, farmKey, cowKey, callback)
    }

    override suspend fun registerNewVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccine: Vaccine
    ) {
        firebaseDataSource.registerNewVaccine(userKey, farmKey, cowKey, vaccine)
    }

    override suspend fun deleteVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String
    ) {
        firebaseDataSource.deleteVaccine(userKey, farmKey, cowKey, vaccineKey)
    }

    override suspend fun editVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        vaccine: Vaccine
    ) {
        firebaseDataSource.editVaccine(userKey, farmKey, cowKey, vaccineKey, vaccine)
    }

    override suspend fun getSingleVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        callback: (Vaccine?) -> Unit
    ) {
        firebaseDataSource.getSingleVaccine(userKey, farmKey, cowKey, vaccineKey, callback)
    }

}