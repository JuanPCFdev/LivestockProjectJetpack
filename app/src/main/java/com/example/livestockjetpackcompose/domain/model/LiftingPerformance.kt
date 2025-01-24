package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LiftingPerformance(
    @SerializedName("PLDate") var PLDate:String = "",
    @SerializedName("PLWeight") var PLWeight:Int = 0,
    @SerializedName("PLDiet") var PLDiet:String = ""
)
