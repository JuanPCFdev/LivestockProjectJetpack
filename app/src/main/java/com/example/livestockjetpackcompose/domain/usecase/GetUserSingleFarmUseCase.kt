package com.example.livestockjetpackcompose.domain.usecase

import android.util.Log
import com.example.livestockjetpackcompose.data.repository.farm.FarmRepository
import com.example.livestockjetpackcompose.domain.model.Farm
import javax.inject.Inject

class GetUserSingleFarmUseCase @Inject constructor(
    private val farmRepository: FarmRepository
) {
    suspend fun getUserSingleFarm(userKey: String, farmKey: String, callback: (Farm?) -> Unit) {
        farmRepository.getUserSingleFarm(userKey, farmKey, callback)
        Log.i("farmId", "se esta accediendo a la finca con key $farmKey")
    }
}