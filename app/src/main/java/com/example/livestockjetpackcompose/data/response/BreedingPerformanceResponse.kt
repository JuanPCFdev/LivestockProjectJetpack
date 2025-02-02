package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import com.google.gson.annotations.SerializedName

data class BreedingPerformanceResponse(
    @SerializedName("PBDateBorn") val PBDate: String = "",
    @SerializedName("PBDateInsemination") val PBDateInsemination: String = "",
    @SerializedName("PBInitialWeight") val PBInitialWeight: Int = 0,
    @SerializedName("PBSick") val PBSick: Boolean = false,
    @SerializedName("PBDeath") val PBDeath: Boolean = false,
    @SerializedName("PBDiet") val PBDiet: String = ""
) {
    fun toDomain(): BreedingPerformance {
        return BreedingPerformance(
            PBDate = PBDate,
            PBInitialWeight = PBInitialWeight,
            PBSick = PBSick,
            PBDeath = PBDeath,
            PBDiet = PBDiet
        )
    }
}
