package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.user.UserRepository
import com.example.livestockjetpackcompose.domain.model.User
import javax.inject.Inject

class GetUserDataFromKeyUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getUserData(userKey: String, callback: (User?) -> Unit) {
        userRepository.getUserData(userKey, callback)
    }
}