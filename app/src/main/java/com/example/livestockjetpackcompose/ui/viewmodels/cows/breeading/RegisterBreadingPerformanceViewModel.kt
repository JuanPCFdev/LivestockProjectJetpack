package com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.usecase.RegisterBreadingPerformanceUseCase
import com.example.livestockjetpackcompose.domain.usecase.RegisterNewCowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RegisterBreadingPerformanceViewModel @Inject constructor(
    private val registerBreadingPerformanceUseCase: RegisterBreadingPerformanceUseCase,
    private val registerNewCowUseCase: RegisterNewCowUseCase
) : ViewModel() {
    //Date it's the same for both class
    private val _marking = MutableStateFlow("")
    val marking: StateFlow<String> get() = _marking

    private val _birthdate = MutableStateFlow("")
    val birthdate: StateFlow<String> get() = _birthdate

    //This is initial weight also
    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> get() = _weight

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> get() = _age

    private val _breed = MutableStateFlow("")
    val breed: StateFlow<String> get() = _breed

    private val _state = MutableStateFlow("")
    val state: StateFlow<String> get() = _state

    private val _sex = MutableStateFlow("")
    val sex: StateFlow<String> get() = _sex

    private val _sick = MutableStateFlow(false)
    val sick: StateFlow<Boolean> get() = _sick

    private val _dead = MutableStateFlow(false)
    val dead: StateFlow<Boolean> get() = _dead

    private val _diet = MutableStateFlow("")
    val diet: StateFlow<String> get() = _diet

    fun onDietChange(newDiet: String) {
        _diet.value = newDiet
    }

    fun onMarkingChange(newMarking: String) {
        if (newMarking.length < 10) {
            _marking.value = newMarking
        }
    }

    fun onBirthdateChange(newBirthdate: String) {
        _birthdate.value = newBirthdate

        _age.value = ageInMonths(newBirthdate).toString()

    }

    fun onWeightChange(newWeight: String) {
        if (newWeight.isNotBlank()) {
            if (newWeight.toInt() < 1600) {
                _weight.value = newWeight
            } else {
                _weight.value = "1600"
            }
        } else {
            _weight.value = newWeight
        }
    }

    fun onAgeChange(newAge: String) {
        _age.value = newAge
    }

    fun onBreedChange(newBreed: String) {
        _breed.value = newBreed
    }

    fun onStateChange(newState: String) {
        _state.value = newState
    }

    fun onSexChange(newSex: String) {
        _sex.value = newSex
    }

    fun onSickChange(newSick: Boolean) {
        _sick.value = newSick
    }

    fun onDeadChange(newDead: Boolean) {
        _dead.value = newDead
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    private fun ageInMonths(birthDate: String): Int {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fNac = dateFormat.parse(birthDate)

        val fechaActual = Calendar.getInstance()

        val calendarioNac = Calendar.getInstance().apply {
            time = fNac
        }

        val años = fechaActual.get(Calendar.YEAR) - calendarioNac.get(Calendar.YEAR)
        val meses = fechaActual.get(Calendar.MONTH) - calendarioNac.get(Calendar.MONTH)

        var edadEnMeses = años * 12 + meses

        if (fechaActual.get(Calendar.DAY_OF_MONTH) < calendarioNac.get(Calendar.DAY_OF_MONTH)) {
            edadEnMeses -= 1
        }

        return if (edadEnMeses < 0) 0 else edadEnMeses
    }


    fun registerNewCow(
        userKey: String,
        farmKey: String,
        cowKey: String,
        onRegisterCowDone: () -> Unit,
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            if (validateInfoCow()) {

                val cow = Cattle(
                    marking = _marking.value,
                    birthdate = _birthdate.value,
                    weight = _weight.value.toInt(),
                    age = _age.value.toInt(),
                    breed = _breed.value,
                    state = _state.value,
                    gender = _sex.value,
                    type = "corral"
                )

                val breadingPerformance = BreedingPerformance(
                    PBDate = _birthdate.value,
                    PBInitialWeight = _weight.value.toInt(),
                    PBSick = _sick.value,
                    PBDeath = _dead.value,
                    PBDiet = _diet.value
                )

                registerNewCowUseCase.registerNewCow(userKey, farmKey, cow)
                registerBreadingPerformanceUseCase.registerBreeadingPerformance(
                    userKey,
                    farmKey,
                    cowKey,
                    breadingPerformance
                )

                delay(1000)
                onRegisterCowDone()
                _uiState.value = UiState.Idle
            } else {
                _uiState.value = UiState.Error("Se deben de llenar todos los campos obligatorios")
            }
        }
    }

    private fun validateInfoCow(): Boolean {
        return (
                _marking.value.isNotBlank()
                        && _sex.value.isNotBlank()
                        && _birthdate.value.isNotBlank()
                        && _breed.value.isNotBlank()
                        && _state.value.isNotBlank()
                        && _weight.value.isNotBlank()
                        && _diet.value.isNotBlank()
                )
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}