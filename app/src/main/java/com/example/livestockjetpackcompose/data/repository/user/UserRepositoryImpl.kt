package com.example.livestockjetpackcompose.data.repository.user

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

    override suspend fun editUser(userKey: String, user: User) {
        firebaseDataSource.editUser(userKey, user)
    }

    override suspend fun deleteUser(key: String) {
        firebaseDataSource.deleteUser(key)
    }

    override suspend fun checkUser(name: String, password: String): Pair<String, User>? {
        return firebaseDataSource.checkUser(name, password)
    }

    override suspend fun getUserData(userKey: String, callback: (User?) -> Unit) {
        firebaseDataSource.getUserData(userKey, callback)
    }

}