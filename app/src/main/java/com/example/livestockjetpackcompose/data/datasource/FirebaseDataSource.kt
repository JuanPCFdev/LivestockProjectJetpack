package com.example.livestockjetpackcompose.data.datasource

import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.model.Insemination
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import com.example.livestockjetpackcompose.domain.model.User
import com.example.livestockjetpackcompose.domain.model.Vaccine

interface FirebaseDataSource {
    suspend fun registerNewUser(user: User)
    suspend fun editUser(userKey: String, user: User)
    suspend fun deleteUser(key: String)
    suspend fun checkUser(name: String, password: String): Pair<String, User>?
    suspend fun registerNewFarm(userKey: String, farm: Farm)
    suspend fun getUserFarms(userKey: String, callback: (List<Farm>?, List<String>?) -> Unit)
    suspend fun getUserSingleFarm(userKey: String, farmKey: String, callback: (Farm?) -> Unit)
    suspend fun deleteFarm(userKey: String, farmKey: String)
    suspend fun getUserData(userKey: String, callback: (User?) -> Unit)
    suspend fun editFarm(farm: Farm, userKey: String, farmKey: String)
    suspend fun getFarmCows(
        userKey: String,
        farmKey: String,
        callback: (List<Cattle>?, List<String>?) -> Unit
    )

    suspend fun registerNewCow(userKey: String, farmKey: String, cow: Cattle)
    suspend fun deleteCow(userKey: String, farmKey: String, cowKey: String)
    suspend fun getSingleCowData(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (Cattle?) -> Unit
    )

    suspend fun editCow(cow: Cattle, userKey: String, farmKey: String, cowKey: String)

    suspend fun getCowVaccines(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Vaccine>?, List<String>?) -> Unit
    )

    suspend fun registerNewVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccine: Vaccine
    )

    suspend fun deleteVaccine(userKey: String, farmKey: String, cowKey: String, vaccineKey: String)

    suspend fun editVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        vaccine: Vaccine
    )

    suspend fun getSingleVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        callback: (Vaccine?) -> Unit
    )

    suspend fun getSingleLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        callback: (LiftingPerformance?) -> Unit
    ) //Temporalmente inutilizado

    suspend fun registerNewLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingPerformance: LiftingPerformance
    )

    suspend fun deleteLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String
    )

    suspend fun editLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String,
        liftingPerformance: LiftingPerformance
    ) //Temporalmente Inutilizado

    suspend fun getAllLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<LiftingPerformance>?, List<String>?) -> Unit
    )

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