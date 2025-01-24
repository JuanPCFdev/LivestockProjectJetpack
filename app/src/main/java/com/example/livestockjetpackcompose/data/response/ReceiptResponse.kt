package com.example.livestockjetpackcompose.data.response

import com.example.livestockjetpackcompose.domain.model.Receipt
import com.google.gson.annotations.SerializedName

data class ReceiptResponse(
    @SerializedName("idReceipt") val idReceipt: Int = 0,
    @SerializedName("nameReceipt") val nameReceipt: String = "",
    @SerializedName("amountPaid") val amountPaid: Double = 0.0,
    @SerializedName("date") val date: String = "",
    @SerializedName("receiptType") val receiptType: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("tel") val tel: String = ""
) {
    fun toDomain(): Receipt {
        return Receipt(
            idReceipt = idReceipt,
            nameReceipt = nameReceipt,
            amountPaid = amountPaid,
            date = date,
            receiptType = receiptType,
            name = name,
            tel = tel
        )
    }
}