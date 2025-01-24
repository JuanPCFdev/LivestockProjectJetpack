package com.example.livestockjetpackcompose.domain.usecase

import com.example.livestockjetpackcompose.data.repository.user.UserRepository
import com.example.livestockjetpackcompose.domain.model.User
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun editUser(userKey: String, user: User) {
        userRepository.editUser(userKey, user)
    }
}