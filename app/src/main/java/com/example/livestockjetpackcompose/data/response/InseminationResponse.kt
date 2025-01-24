package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.Insemination
import com.google.gson.annotations.SerializedName

data class InseminationResponse(
    @SerializedName("inseminationDate") val inseminationDate: String = "",
    @SerializedName("descOfInsemination") val descOfInsemination: String = "",
    @SerializedName("spermOrigin") val spermOrigin: String = ""
) {
    fun toDomain(): Insemination {
        return Insemination(
            inseminationDate = inseminationDate,
            descOfInsemination = descOfInsemination,
            spermOrigin = spermOrigin
        )
    }
}
