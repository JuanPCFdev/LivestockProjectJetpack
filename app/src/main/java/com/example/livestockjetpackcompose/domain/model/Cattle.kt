package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Cattle(
    @SerializedName("marking") var marking: String = "",
    @SerializedName("birthdate") var birthdate: String = "",
    @SerializedName("weight") var weight: Int = 0,
    @SerializedName("age") var age: Int = 0,
    @SerializedName("breed") var breed: String = "",
    @SerializedName("state") var state: String = "",
    @SerializedName("gender") var gender: String = "",
    @SerializedName("type") var type: String = "",
    @SerializedName("motherMark") var motherMark: String = "",
    @SerializedName("fatherMark") var fatherMark: String = "",
    @SerializedName("cost") var cost: Double = 0.0,
    @SerializedName("castrated") var castrated: Boolean = false,
    @SerializedName("vaccines") var vaccines: MutableList<Vaccine> = emptyList<Vaccine>().toMutableList(),
    @SerializedName("PLifting") var PLifting: MutableList<LiftingPerformance> = emptyList<LiftingPerformance>().toMutableList(),
    @SerializedName("PBreeding") var PBreeding: MutableList<BreedingPerformance> = emptyList<BreedingPerformance>().toMutableList(),
    @SerializedName("Death") var death: DeathDetails = DeathDetails(),
    @SerializedName("inseminationNews") var inseminationNews: MutableList<Insemination> = emptyList<Insemination>().toMutableList()
)
