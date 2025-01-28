package com.example.livestockjetpackcompose.data.datasource

import android.util.Log
import androidx.compose.animation.core.snap
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
        const val USER_FARM_CATTLE_VACCINE_REF = "vaccines"
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
                Log.i("Editar Finca Error", error.details)
            }
        })
    }

    //CATTLE

    override suspend fun getFarmCows(
        userKey: String,
        farmKey: String,
        callback: (List<Cattle>?, List<String>?) -> Unit
    ) {
        val userReference = myRef.child(userKey)
            .child(USER_FARM_REF)
            .child(farmKey)
            .child(USER_FARM_CATTLE_REF)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val farmCattleList = mutableListOf<Cattle>()
                val farmCattleKeysList = mutableListOf<String>()
                for (cattleSnapshot in snapshot.children) {
                    val key = cattleSnapshot.key ?: ""
                    val cattleData = cattleSnapshot.getValue(Cattle::class.java)
                    if (cattleData != null) {
                        farmCattleList.add(cattleData)
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

    override suspend fun deleteCow(userKey: String, farmKey: String, cowKey: String) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)

                    val cattleRemove =
                        existingUser?.farms?.get(farmKey.toInt())?.cattles?.get(cowKey.toInt())

                    existingUser?.farms?.get(farmKey.toInt())?.cattles?.remove(cattleRemove)

                    userReference.setValue(existingUser)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(
                    "Fallo al intentar eliminar vaca con key${cowKey}, en el usuario ${userKey}, en finca ${farmKey}",
                    error.details
                )
            }

        })

    }

    override suspend fun getSingleCowData(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (Cattle?) -> Unit
    ) {
        val userReference = myRef.child(userKey)
            .child(USER_FARM_REF)
            .child(farmKey)
            .child(USER_FARM_CATTLE_REF)
            .child(cowKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingCattle = snapshot.getValue(Cattle::class.java)

                    if (existingCattle != null) {
                        callback(existingCattle)
                    } else {
                        Log.i("Obtener Datos Vaca", "El valor de la base de datos es nulo")
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
                Log.i("Obtener Datos Vaca", error.message)
            }

        })

    }

    override suspend fun editCow(cow: Cattle, userKey: String, farmKey: String, cowKey: String) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)

                    existingUser?.farms?.get(farmKey.toInt())?.cattles?.get(cowKey.toInt()).apply {
                        this?.marking = cow.marking
                        this?.birthdate = cow.birthdate
                        this?.weight = cow.weight
                        this?.age = cow.age
                        this?.breed = cow.breed
                        this?.state = cow.state
                        this?.gender = cow.gender
                        this?.type = cow.type
                        this?.motherMark = cow.motherMark
                        this?.fatherMark = cow.fatherMark
                        this?.cost = cow.cost
                        this?.castrated = cow.castrated
                    }

                    userReference.setValue(existingUser)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Editar Vaca Error", error.details)
            }

        })
    }

    //VACCINE

    override suspend fun getCowVaccines(
        userKey: String,
        farmKey: String,
        cowKey: String,
        callback: (List<Vaccine>?, List<String>?) -> Unit
    ) {
        val userReference = myRef.child(userKey)
            .child(USER_FARM_REF)
            .child(farmKey)
            .child(USER_FARM_CATTLE_REF)
            .child(cowKey)
            .child(USER_FARM_CATTLE_VACCINE_REF)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val cattleVaccines: MutableList<Vaccine> = mutableListOf<Vaccine>()
                    val vaccineKeys: MutableList<String> = mutableListOf<String>()
                    for (vaccineSnapshot in snapshot.children) {
                        val key = vaccineSnapshot.key ?: ""
                        val vaccine = vaccineSnapshot.getValue(Vaccine::class.java)
                        if (vaccine != null) {
                            cattleVaccines.add(vaccine)
                            vaccineKeys.add(key)
                        }
                    }
                    callback(cattleVaccines, vaccineKeys)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, null)
            }

        })

    }

    override suspend fun registerNewVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccine: Vaccine
    ) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)
                    val farm = farmKey.toInt()
                    val cow = cowKey.toInt()

                    if (existingUser != null) {
                        existingUser.farms[farm].cattles[cow].vaccines.add(vaccine)
                        userReference.setValue(existingUser)
                    } else {
                        Log.i("Register New Vaccine", "Error, el usuario no existe")
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(
                    "Register New Vaccine",
                    "No se ha logrado registrar la vacuna correctamente ${error.message}"
                )
            }

        })

    }

    override suspend fun deleteVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String
    ) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)

                    if (existingUser != null) {
                        val vaccineRemove =
                            existingUser.farms[farmKey.toInt()].cattles[cowKey.toInt()].vaccines[vaccineKey.toInt()]

                        existingUser.farms[farmKey.toInt()].cattles[cowKey.toInt()].vaccines.remove(
                            vaccineRemove
                        )

                        userReference.setValue(existingUser)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(
                    "Fallo al intentar eliminar vacuna con key${vaccineKey}, en el usuario ${userKey}, en finca ${farmKey}, en vaca${cowKey}",
                    error.details
                )
            }

        })
    }

    override suspend fun editVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        vaccine: Vaccine
    ) {
        val userReference = myRef.child(userKey)

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val existingUser = snapshot.getValue(User::class.java)
                    if (existingUser != null) {
                        existingUser.farms[farmKey.toInt()].cattles[cowKey.toInt()].vaccines[vaccineKey.toInt()].apply {
                            this.vaccineName = vaccine.vaccineName
                            this.vaccineCost = vaccine.vaccineCost
                            this.date = vaccine.date
                            this.supplier = vaccine.supplier
                        }
                        userReference.setValue(existingUser)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Editar Vacuna Error", error.details)
            }

        })

    }

    override suspend fun getSingleVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        callback: (Vaccine?) -> Unit
    ) {
        val vaccineReference = myRef.child(userKey)
            .child(USER_FARM_REF)
            .child(farmKey)
            .child(USER_FARM_CATTLE_REF)
            .child(cowKey)
            .child(USER_FARM_CATTLE_VACCINE_REF)
            .child(vaccineKey)

        vaccineReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val existingVaccine = snapshot.getValue(Vaccine::class.java)
                    if (existingVaccine!=null){
                        callback(existingVaccine)
                    }else{
                        callback(null)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }

        })

    }

}