package com.example.livestockjetpackcompose.data.repository

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : UserRepository {

    override suspend fun registerNewUser(user: User) {
        firebaseDataSource.registerNewUser(user)
    }

    override suspend fun editUser(user: User, key: String) {
        firebaseDataSource.editUser(user, key)
    }

    override suspend fun deleteUser(key: String) {
        firebaseDataSource.deleteUser(key)
    }

    override suspend fun checkUser(name: String, password: String):User?{
        return firebaseDataSource.checkUser(name,password)
    }

}