package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Farm(
    @SerializedName("nameFarm") var nameFarm:String = "",
    @SerializedName("hectares") var hectares: Double = 0.0,
    @SerializedName("numCows") var numCows: Int = 0,
    @SerializedName("address") var address:String = "",
    @SerializedName("cattles") var cattles: MutableList<Cattle> = emptyList<Cattle>().toMutableList(),
    @SerializedName("receipts") var receipts: MutableList<Receipt> = emptyList<Receipt>().toMutableList()
)
