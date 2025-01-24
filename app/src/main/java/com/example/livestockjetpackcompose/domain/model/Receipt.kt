package com.example.livestockjetpackcompose.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Receipt(
    @SerializedName("idReceipt") var idReceipt: Int = 0,
    @SerializedName("nameReceipt") var nameReceipt: String = "",
    @SerializedName("amountPaid") var amountPaid:Double = 0.0,
    @SerializedName("date") var date: String = "",
    @SerializedName("receiptType") var receiptType:String = "",
    @SerializedName("name") var name:String = "",
    @SerializedName("tel") var tel:String = ""
)
