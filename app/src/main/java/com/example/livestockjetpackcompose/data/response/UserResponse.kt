package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("userId") val userId: String = "",
    @SerializedName("username") val name: String = "",
    @SerializedName("password") val password: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("farms") val farms: MutableList<Farm> = mutableListOf()
){
    fun toDomain():User{
        return User(
            userId = userId,
            name = name,
            password = password,
            phone = phone,
            farms = farms
        )
    }
}
