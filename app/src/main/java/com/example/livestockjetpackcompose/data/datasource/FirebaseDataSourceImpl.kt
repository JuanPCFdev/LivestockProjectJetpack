package com.example.livestockjetpackcompose.data.datasource

import android.util.Log
import com.example.livestockjetpackcompose.data.response.UserResponse
import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.model.DeathDetails
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.model.Insemination
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import com.example.livestockjetpackcompose.domain.model.Receipt
import com.example.livestockjetpackcompose.domain.model.User
import com.example.livestockjetpackcompose.domain.model.Vaccine
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseDataSourceImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : FirebaseDataSource {

    companion object {
        const val USER_NAME_REF = "name"
        const val USER_FARM_REF = "farms"
        const val USER_FARM_CATTLE_REF = "cattles"
    }

    private val myRef = firebaseDatabase.reference

    //USER

    override suspend fun registerNewUser(user: User) {
        val newUser = myRef.push()
        newUser.setValue(user).await()
    }

    override suspend fun deleteUser(key: String) {
        key.let {
            myRef.child(it).setValue(null).await()
        }
    }

    override suspend fun checkUser(
        name: String,
        password: String
    ): Pair<String, User>? = suspendCoroutine { cont ->

        myRef.orderByChild(USER_NAME_REF).equalTo(name).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val userKey = userSnapshot.key ?: continue
                            val userData: Map<String, Any>? =
                                userSnapshot.getValue(object :
                                    GenericTypeIndicator<Map<String, Any>>() {})

                            if (userData != null) {
                                val user = User(
                                    userId = userData["userId"] as? String ?: "",
                                    name = userData["name"] as? String ?: "",
                                    password = userData["password"] as? String ?: "",
                                    phone = userData["phone"] as? String ?: "",
                                    farms = userData["farms"] as? MutableList<Farm>
                                        ?: mutableListOf()
                                )

                                if (user.name == name && user.password == password) {
                                    cont.resume(Pair(userKey, user))
                                    return
                                }
                            }
                        }
                    }
                    cont.resume(null)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error: ${error.message}")
                    cont.resume(null)
                }
            }
        )
    }


    override suspend fun editUser(userKey: String, user: User) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)

                    existingUser?.apply {
                        this.name = user.name
                        this.phone = user.phone
                        this.password = user.password
                    }

                    userReference.setValue(existingUser)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Fallo al intentar editar usuario", error.details)
            }

        })

    }

    override suspend fun getUserData(userKey: String, callback: (User?) -> Unit) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(UserResponse::class.java)?.toDomain()
                    if (existingUser != null) {
                        callback(existingUser)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }

        })
    }

    //FARM

    override suspend fun registerNewFarm(userKey: String, farm: Farm) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(UserResponse::class.java)?.toDomain()
                    existingUser?.farms?.add(farm)
                    userReference.setValue(existingUser)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("No se ha logrado registrar una finca", error.message)
            }
        })

    }

    override suspend fun getUserFarms(
        userKey: String,
        callback: (List<Farm>?, List<String>?) -> Unit
    ) {
        val userReference = myRef.child(userKey).child(USER_FARM_REF)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val farms = mutableListOf<Farm>()
                val farmKeys = mutableListOf<String>()

                for (farmSnapshot in snapshot.children) {
                    val key = farmSnapshot.key ?: ""
                    val farmData: Map<String, Any>? =
                        farmSnapshot.getValue(object : GenericTypeIndicator<Map<String, Any>>() {})

                    if (farmData != null) {

                        val farm = Farm(
                            nameFarm = farmData["nameFarm"] as? String ?: "",
                            hectares = farmData["hectares"].toString().toDouble() as? Double ?: 0.0,
                            numCows = farmData["numCows"].toString().toInt() as? Int ?: 0,
                            address = farmData["address"] as? String ?: "",
                            cattles = farmData["cattles"] as? MutableList<Cattle>
                                ?: mutableListOf(),
                            receipts = farmData["receipts"] as? MutableList<Receipt>
                                ?: mutableListOf()
                        )

                        farms.add(farm)
                        farmKeys.add(key)
                    }
                }
                callback(farms, farmKeys)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, null)
            }
        })

    }

    override suspend fun getUserSingleFarm(
        userKey: String,
        farmKey: String,
        callback: (Farm?) -> Unit
    ) {
        val userReference = myRef.child(userKey).child(USER_FARM_REF)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val farmData = mutableListOf<Farm>()

                for (farmSnapshot in snapshot.children) {
                    val farmDB: Map<String, Any>? =
                        farmSnapshot.getValue(object : GenericTypeIndicator<Map<String, Any>>() {})

                    if (farmDB != null) {
                        val farm = Farm(
                            nameFarm = farmDB["nameFarm"] as? String ?: "",
                            hectares = farmDB["hectares"].toString().toDouble() as? Double ?: 0.0,
                            numCows = farmDB["numCows"].toString().toInt() as? Int ?: 0,
                            address = farmDB["address"] as? String ?: "",
                            cattles = farmDB["cattles"] as? MutableList<Cattle>
                                ?: mutableListOf(),
                            receipts = farmDB["receipts"] as? MutableList<Receipt>
                                ?: mutableListOf()
                        )
                        farmData.add(farm)
                    }
                }
                callback(farmData[farmKey.toInt()])
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }

        })

    }

    override suspend fun deleteFarm(userKey: String, farmKey: String) {

        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)
                    existingUser?.farms?.removeAt(farmKey.toInt())
                    userReference.setValue(existingUser)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(
                    "Fallo al intentar eliminar finca con key${farmKey}, en el usuario ${userKey}",
                    error.details
                )
            }
        })

    }

    override suspend fun editFarm(farm: Farm, userKey: String, farmKey: String) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)

                    existingUser?.farms?.get(farmKey.toInt())?.apply {
                        this.nameFarm = farm.nameFarm
                        this.hectares = farm.hectares
                        this.numCows = farm.numCows
                        this.address = farm.address
                    }

                    userReference.setValue(existingUser)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Fallo al intentar editar finca", error.details)
            }
        })
    }

    //CATTLE

    override suspend fun getFarmCows(
        userKey: String,
        farmKey: String,
        callback: (List<Cattle>?, List<String>?) -> Unit
    ) {
        val userReference = myRef.child(userKey).child(USER_FARM_REF).child(USER_FARM_CATTLE_REF)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val farmCattleList = mutableListOf<Cattle>()
                val farmCattleKeysList = mutableListOf<String>()

                for (cattleSnapshot in snapshot.children) {
                    val key = cattleSnapshot.key ?: ""
                    val cattleData: Map<String, Any>? =
                        cattleSnapshot.getValue(object :
                            GenericTypeIndicator<Map<String, Any>>() {})

                    if (cattleData != null) {

                        val cattle = Cattle(
                            marking = cattleData["marking"] as? String ?: "",
                            birthdate = cattleData["birthdate"] as? String ?: "",
                            weight = cattleData["weight"].toString().toInt() as? Int ?: 0,
                            age = cattleData["age"].toString().toInt() as? Int ?: 0,
                            breed = cattleData["breed"] as? String ?: "",
                            state = cattleData["state"] as? String ?: "",
                            gender = cattleData["gender"] as? String ?: "",
                            type = cattleData["type"] as? String ?: "",
                            motherMark = cattleData["motherMark"] as? String ?: "",
                            fatherMark = cattleData["fatherMark"] as? String ?: "",
                            cost = cattleData["cost"].toString().toDouble() as? Double ?: 0.0,
                            castrated = cattleData["castrated"].toString().toBoolean() as? Boolean
                                ?: false,
                            vaccines = cattleData["vaccines"] as? MutableList<Vaccine>
                                ?: mutableListOf(),
                            PLifting = cattleData["PLifting"] as? MutableList<LiftingPerformance>
                                ?: mutableListOf(),
                            PBreeding = cattleData["PBreeding"] as? MutableList<BreedingPerformance>
                                ?: mutableListOf(),
                            death = cattleData["Death"] as? DeathDetails ?: DeathDetails(),
                            inseminationNews = cattleData["inseminationNews"] as? MutableList<Insemination>
                                ?: mutableListOf()
                        )

                        farmCattleList.add(cattle)
                        farmCattleKeysList.add(key)
                    }
                }
                callback(farmCattleList, farmCattleKeysList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, null)
            }

        })
    }

    override suspend fun registerNewCow(userKey: String, farmKey: String, cow: Cattle) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)
                    val farm = farmKey.toInt()

                    val isMarkingUnique = existingUser?.farms?.get(farm)?.cattles
                        ?.none {
                            it.marking == cow.marking
                        } ?: true

                    if (isMarkingUnique) {
                        existingUser?.farms?.get(farm)?.cattles?.add(cow)
                        userReference.setValue(existingUser)
                    } else {
                        Log.i("Register New Cow", "Ya existe una vaca con la misma marcación")
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(
                    "Register New Cow",
                    "No se ha logrado registrar una vaca correctamente ${error.message}"
                )
            }

        })

    }

}