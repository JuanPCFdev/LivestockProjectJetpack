package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BreedingPerformance(
    @SerializedName("PBDateBorn") var PBDate: String = "",
    @SerializedName("PBDateInsemination") var PBDateInsemination:String ="",
    @SerializedName("PBInitialWeight") var PBInitialWeight:Int = 0,
    @SerializedName("PBSick") var PBSick:Boolean = false,
    @SerializedName("PBDeath") var PBDeath:Boolean = false,
    @SerializedName("PBDiet") var PBDiet:String = ""
)
