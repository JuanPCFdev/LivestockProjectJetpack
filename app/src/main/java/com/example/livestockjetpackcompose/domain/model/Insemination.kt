package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Insemination(
    @SerializedName("inseminationDate") var inseminationDate: String = "",
    @SerializedName("descOfInsemination") var descOfInsemination: String = "",
    @SerializedName("spermOrigin") var spermOrigin: String = ""
)
