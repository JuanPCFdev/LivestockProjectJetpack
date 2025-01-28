package com.example.livestockjetpackcompose.ui.viewmodels.cows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.usecase.EditCowUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetCowDataUseCase
import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
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
class CowResumeViewModel @Inject constructor(
    private val getCowDataUseCase: GetCowDataUseCase,
    private val editCowUseCase: EditCowUseCase
) : ViewModel() {

    private val _marking = MutableStateFlow("")
    val marking: StateFlow<String> get() = _marking

    private val _birthdate = MutableStateFlow("")
    val birthdate: StateFlow<String> get() = _birthdate

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

    private val _markingFather = MutableStateFlow("")
    val markingFather: StateFlow<String> get() = _markingFather

    private val _markingMother = MutableStateFlow("")
    val markingMother: StateFlow<String> get() = _markingMother

    private val _castrated = MutableStateFlow(false)
    val castrated: StateFlow<Boolean> get() = _castrated

    private val _cost = MutableStateFlow("")
    val cost: StateFlow<String> get() = _cost

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState


    fun getCowData(userKey: String, farmKey: String, cowKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getCowDataUseCase.getCowData(userKey, farmKey, cowKey) { cow ->
                if (cow != null) {
                    _marking.value = cow.marking
                    _birthdate.value = cow.birthdate
                    _weight.value = cow.weight.toString()
                    _age.value = cow.age.toString()
                    _breed.value = cow.breed
                    _state.value = cow.state
                    _sex.value = cow.gender
                    _markingFather.value = cow.fatherMark
                    _markingMother.value = cow.motherMark
                    _castrated.value = cow.castrated
                    _cost.value = cow.cost.toString()
                }
            }

            _uiState.value = UiState.Idle
        }
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

    fun onMarkingFatherChange(newMarkingFather: String) {
        if (newMarkingFather.length < 10) {
            _markingFather.value = newMarkingFather
        }
    }

    fun onMarkingMotherChange(newMarkingMother: String) {
        if (newMarkingMother.length < 10) {
            _markingMother.value = newMarkingMother
        }
    }

    fun onCastratedChange(castrated: Boolean) {
        _castrated.value = castrated
    }

    fun onCostChange(newCost: String) {
        if (newCost.length < 16) {
            _cost.value = newCost
        }
    }


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

    fun editCow(userKey: String, farmKey: String, cowKey: String, cowType: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val newCow = createCow(cowType)
            editCowUseCase.editCow(newCow, userKey, farmKey, cowKey)
            delay(1000)
            getCowData(userKey = userKey, farmKey = farmKey, cowKey = cowKey)
            delay(500)
            _uiState.value = UiState.Idle
        }
    }

    private fun createCow(cowType: String): Cattle {

        var cost: Double = 0.0

        if (_cost.value.isNotBlank()) {
            cost = _cost.value.toDouble()
        }

        return Cattle(
            marking = _marking.value,
            birthdate = _birthdate.value,
            weight = _weight.value.toInt(),
            age = _age.value.toInt(),
            breed = _breed.value,
            state = _state.value,
            gender = _sex.value,
            type = cowType,
            motherMark = _markingMother.value,
            fatherMark = _markingFather.value,
            cost = cost,
            castrated = _castrated.value,
        )
    }

    private fun validateInfoCow(): Boolean {
        return (
                _marking.value.isNotBlank()
                        && _sex.value.isNotBlank()
                        && _birthdate.value.isNotBlank()
                        && _breed.value.isNotBlank()
                        && _state.value.isNotBlank()
                        && _cost.value.isNotBlank()
                )
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}