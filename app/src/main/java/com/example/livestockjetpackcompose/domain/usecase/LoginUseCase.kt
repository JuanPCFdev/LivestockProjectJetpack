package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.user.UserRepository
import com.example.livestockjetpackcompose.domain.model.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun login(userName: String, password: String): Pair<String,User>? {
        return userRepository.checkUser(userName, password)
    }
}