package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class DeathDetails(
    @SerializedName("deathDate") var deathDate:String = "",
    @SerializedName("deathCause") var deathCause:String = "",
    @SerializedName("deathDescription") var deathDescription:String = ""
)
