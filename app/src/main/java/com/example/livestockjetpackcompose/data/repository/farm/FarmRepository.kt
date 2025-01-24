package com.example.livestockjetpackcompose.data.repository.farm

import com.example.livestockjetpackcompose.domain.model.Farm

interface FarmRepository {
    suspend fun registerNewFarm(userKey:String, farm:Farm)
    suspend fun getUserFarms(userKey: String, callback: (List<Farm>?, List<String>?) -> Unit)
    suspend fun getUserSingleFarm(userKey: String, farmKey: String, callback: (Farm?) -> Unit)
    suspend fun deleteFarm(userKey: String, farmKey: String)
    suspend fun editFarm(farm: Farm, userKey: String, farmKey: String)
}