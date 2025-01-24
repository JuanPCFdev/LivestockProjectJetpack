package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.model.DeathDetails
import com.example.livestockjetpackcompose.domain.model.Insemination
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import com.example.livestockjetpackcompose.domain.model.Vaccine
import com.google.gson.annotations.SerializedName

data class CattleResponse(
    @SerializedName("cattleId") val cattleId: Int = 0,
    @SerializedName("marking") val marking: String = "",
    @SerializedName("birthdate") val birthdate: String = "",
    @SerializedName("weight") val weight: Int = 0,
    @SerializedName("age") val age: Int = 0,
    @SerializedName("breed") val breed: String = "",
    @SerializedName("state") val state: String = "",
    @SerializedName("gender") val gender: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("motherMark") val motherMark: String = "",
    @SerializedName("fatherMark") val fatherMark: String = "",
    @SerializedName("cost") val cost: Double = 0.0,
    @SerializedName("castrated") val castrated: Boolean = false,
    @SerializedName("vaccines") val vaccines: MutableList<Vaccine> = emptyList<Vaccine>().toMutableList(),
    @SerializedName("PLifting") val PLifting: MutableList<LiftingPerformance> = emptyList<LiftingPerformance>().toMutableList(),
    @SerializedName("PBreeding") val PBreeding: MutableList<BreedingPerformance> = emptyList<BreedingPerformance>().toMutableList(),
    @SerializedName("Death") val death: DeathDetails = DeathDetails(),
    @SerializedName("inseminationNews") val inseminationNews: MutableList<Insemination> = emptyList<Insemination>().toMutableList()
) {
    fun toDomain(): Cattle {
        return Cattle(
            marking = marking,
            birthdate = birthdate,
            weight = weight,
            age = age,
            breed = breed,
            state = state,
            gender = gender,
            type = type,
            motherMark = motherMark,
            fatherMark = fatherMark,
            cost = cost,
            castrated = castrated,
            vaccines = vaccines,
            PLifting = PLifting,
            PBreeding = PBreeding,
            death = death,
            inseminationNews = inseminationNews
        )
    }
}