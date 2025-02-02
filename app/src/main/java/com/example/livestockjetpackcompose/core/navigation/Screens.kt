package com.example.livestockjetpackcompose.core.navigation

import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
data class ListFarm(
    val userKey: String
)

@Serializable
object RegisterUser

@Serializable
data class RegisterFarm(
    val userKey: String
)

@Serializable
data class HomePage(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class EditFarm(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class EditUser(
    val userKey: String
)

@Serializable
data class CowHomePage(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class FinanceHome(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class BreedingHomeCow(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class LiftingHomeCow(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class CorralHomeCow(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class DeadCowHome(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class SoldCowHome(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class RegisterLiftingCow(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class RegisterBreedingCow(
    val userKey: String,
    val farmKey: String
)

@Serializable
data class CowResume(
    val userKey: String,
    val farmKey: String,
    val cowKey: String,
    val cowType: CowTypeFilter
)

@Serializable
data class VaccineHome(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)

@Serializable
data class RegisterVaccine(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)

@Serializable
data class EditVaccine(
    val userKey: String,
    val farmKey: String,
    val cowKey: String,
    val vaccineKey: String
)

@Serializable
data class LiftingStats(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)

@Serializable
data class RegisterLiftingPerformance(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)

@Serializable
data class InseminationStats(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)

@Serializable
data class RegisterInsemination(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)

@Serializable
data class BreadingStats(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)

@Serializable
data class RegisterBreadingPerformance(
    val userKey: String,
    val farmKey: String,
    val cowKey: String
)