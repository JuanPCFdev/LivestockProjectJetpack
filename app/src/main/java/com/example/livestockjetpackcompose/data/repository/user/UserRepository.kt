package com.example.livestockjetpackcompose.data.repository.user

import com.example.livestockjetpackcompose.domain.model.User

interface UserRepository {
    suspend fun registerNewUser(user: User)
    suspend fun editUser(userKey: String, user:User)
    suspend fun deleteUser(key: String)
    suspend fun checkUser(name: String, password: String): Pair<String, User>?
    suspend fun getUserData(userKey: String,callback: (User?) -> Unit)
}