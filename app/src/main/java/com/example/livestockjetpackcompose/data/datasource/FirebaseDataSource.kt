package com.example.livestockjetpackcompose.data.datasource

import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.model.User

interface FirebaseDataSource {
    suspend fun registerNewUser(user: User)
    suspend fun editUser(userKey: String, user: User)
    suspend fun deleteUser(key: String)
    suspend fun checkUser(name: String, password: String): Pair<String, User>?
    suspend fun registerNewFarm(userKey: String, farm: Farm)
    suspend fun getUserFarms(userKey: String, callback: (List<Farm>?, List<String>?) -> Unit)
    suspend fun getUserSingleFarm(userKey: String, farmKey: String, callback: (Farm?) -> Unit)
    suspend fun deleteFarm(userKey: String, farmKey: String)
    suspend fun getUserData(userKey: String, callback: (User?) -> Unit)
    suspend fun editFarm(farm: Farm, userKey: String, farmKey: String)
    suspend fun getFarmCows(userKey: String, farmKey: String, callback: (List<Cattle>?, List<String>?) -> Unit)
    suspend fun registerNewCow(userKey: String, farmKey: String, cow:Cattle)
    suspend fun deleteCow(userKey: String, farmKey: String, cowKey:String)
}