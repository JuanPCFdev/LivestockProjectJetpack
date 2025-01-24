package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Vaccine(
    @SerializedName("idVaccine") var idVaccine: Int = 0,
    @SerializedName("vaccineName") var vaccineName: String = "",
    @SerializedName("vaccineCost") var vaccineCost:Double = 0.0,
    @SerializedName("date") var date: String = "",
    @SerializedName("supplier") var supplier:String = ""
)
