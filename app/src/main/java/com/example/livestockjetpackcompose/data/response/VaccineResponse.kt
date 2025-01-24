package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.Vaccine
import com.google.gson.annotations.SerializedName

data class VaccineResponse(
    @SerializedName("idVaccine") val idVaccine: Int = 0,
    @SerializedName("vaccineName") val vaccineName: String = "",
    @SerializedName("vaccineCost") val vaccineCost: Double = 0.0,
    @SerializedName("date") val date: String = "",
    @SerializedName("supplier") val supplier: String = ""
) {
    fun toDomain(): Vaccine {
        return Vaccine(
            idVaccine = idVaccine,
            vaccineName = vaccineName,
            vaccineCost = vaccineCost,
            date = date,
            supplier = supplier
        )
    }
}

