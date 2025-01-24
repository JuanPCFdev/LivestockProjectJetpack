package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import com.google.gson.annotations.SerializedName

data class LiftingPerformanceResponse(
    @SerializedName("PLDate") val PLDate: String = "",
    @SerializedName("PLWeight") val PLWeight: Int = 0,
    @SerializedName("PLDiet") val PLDiet: String = ""
) {
    fun toDomain(): LiftingPerformance {
        return LiftingPerformance(
            PLDate = PLDate,
            PLWeight = PLWeight,
            PLDiet = PLDiet
        )
    }
}
