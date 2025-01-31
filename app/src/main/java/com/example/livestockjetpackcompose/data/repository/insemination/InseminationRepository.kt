package com.example.livestockjetpackcompose.data.repository.insemination

import com.example.livestockjetpackcompose.domain.model.Insemination

interface InseminationRepository {

    suspend fun registerNewInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        insemination: Insemination
    )

    suspend fun deleteInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        inseminationKey: String
    )

    suspend fun getAllInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Insemination>?, List<String>?) -> Unit
    )

}