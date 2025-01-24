package com.example.livestockjetpackcompose.data.repository.farm

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.domain.model.Farm
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FarmRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : FarmRepository {

    override suspend fun registerNewFarm(userKey: String, farm: Farm) {
        firebaseDataSource.registerNewFarm(userKey, farm)
    }

    override suspend fun getUserFarms(
        userKey: String,
        callback: (List<Farm>?, List<String>?) -> Unit
    ) {
        firebaseDataSource.getUserFarms(userKey, callback)
    }

    override suspend fun getUserSingleFarm(
        userKey: String,
        farmKey: String,
        callback: (Farm?) -> Unit
    ) {
        firebaseDataSource.getUserSingleFarm(userKey, farmKey, callback)
    }

    override suspend fun deleteFarm(userKey: String, farmKey: String) {
        firebaseDataSource.deleteFarm(userKey, farmKey)
    }

    override suspend fun editFarm(farm: Farm, userKey: String, farmKey: String) {
        firebaseDataSource.editFarm(farm, userKey, farmKey)
    }

}