package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.DeathDetails
import com.google.gson.annotations.SerializedName

data class DeathDetailsResponse(
    @SerializedName("deathDate") val deathDate: String = "",
    @SerializedName("deathCause") val deathCause: String = "",
    @SerializedName("deathDescription") val deathDescription: String = ""
) {
    fun toDomain(): DeathDetails {
        return DeathDetails(
            deathDate = deathDate,
            deathCause = deathCause,
            deathDescription = deathDescription
        )
    }
}
