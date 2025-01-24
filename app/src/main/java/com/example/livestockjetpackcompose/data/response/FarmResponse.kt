package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.model.Receipt
import com.google.gson.annotations.SerializedName

data class FarmResponse(
    @SerializedName("nameFarm") val nameFarm:String = "",
    @SerializedName("hectares") val hectares: Double = 0.0,
    @SerializedName("numCows") val numCows: Int = 0,
    @SerializedName("address") val address:String = "",
    @SerializedName("cattles") val cattles: MutableList<Cattle> = emptyList<Cattle>().toMutableList(),
    @SerializedName("receipts") val receipts: MutableList<Receipt> = emptyList<Receipt>().toMutableList()
){
    fun toDomain():Farm{
        return Farm(
            nameFarm = nameFarm,
            hectares = hectares,
             numCows = numCows,
            address = address,
            cattles = cattles,
             receipts = receipts
        )
    }
}
