package com.example.livestockjetpackcompose.data.repository.cattle

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.domain.model.Cattle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CattleRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : CattleRepository {


    override suspend fun getUserFarmCows(
        userKey: String,
        farmKey: String,
        callback: (List<Cattle>?, List<String>?) -> Unit
    ) {
        firebaseDataSource.getFarmCows(userKey, farmKey, callback)
    }

    override suspend fun registerNewCow(userKey: String, farmKey: String, cow: Cattle) {
        firebaseDataSource.registerNewCow(userKey, farmKey, cow)
    }

    override suspend fun deleteCow(userKey: String, farmKey: String, cowKey: String) {
        firebaseDataSource.deleteCow(userKey, farmKey, cowKey)
    }

    override suspend fun getCowData(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (Cattle?) -> Unit
    ) {
        firebaseDataSource.getSingleCowData(userKey, farmKey, cowKey, callback)
    }

    override suspend fun editCow(cow: Cattle, userKey: String, farmKey: String, cowKey: String) {
        firebaseDataSource.editCow(cow, userKey, farmKey, cowKey)
    }

}