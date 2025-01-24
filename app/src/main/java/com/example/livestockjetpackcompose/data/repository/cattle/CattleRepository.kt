package com.example.livestockjetpackcompose.data.repository.cattle

import com.example.livestockjetpackcompose.domain.model.Cattle

interface CattleRepository {
    suspend fun getUserFarmCows(userKey: String, farmKey: String, callback: (List<Cattle>?,List<String>?) -> Unit)
    suspend fun registerNewCow(userKey: String, farmKey: String, cattle: Cattle)
}