package com.example.livestockjetpackcompose.data.repository

import com.example.livestockjetpackcompose.domain.model.User

interface UserRepository {
    suspend fun registerNewUser(user: User)
    suspend fun editUser(user:User, key:String)
    suspend fun deleteUser(key: String)
    suspend fun checkUser(name:String,password:String):User?
}