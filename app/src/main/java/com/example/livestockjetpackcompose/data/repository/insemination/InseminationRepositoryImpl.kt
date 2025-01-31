package com.example.livestockjetpackcompose.data.repository.insemination

import com.example.livestockjetpackcompose.data.datasource.FirebaseDataSource
import com.example.livestockjetpackcompose.domain.model.Insemination
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InseminationRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) : InseminationRepository {

    override suspend fun registerNewInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        insemination: Insemination
    ) {
        firebaseDataSource.registerNewInsemination(userKey, farmKey, cowKey, insemination)
    }

    override suspend fun deleteInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        inseminationKey: String
    ) {
        firebaseDataSource.deleteInsemination(userKey, farmKey, cowKey, inseminationKey)
    }

    override suspend fun getAllInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Insemination>?, List<String>?) -> Unit
    ) {
        firebaseDataSource.getAllInsemination(userKey, farmKey, cowKey, callback)
    }
}