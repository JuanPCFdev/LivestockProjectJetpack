package com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Vaccine
import com.example.livestockjetpackcompose.domain.usecase.EditVaccineUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetSingleVaccineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditVaccineViewModel @Inject constructor(
    private val editVaccineUseCase: EditVaccineUseCase,
    private val getSingleVaccineUseCase: GetSingleVaccineUseCase
) : ViewModel() {

    private val _vaccineName = MutableStateFlow("")
    val vaccineName: StateFlow<String> get() = _vaccineName

    private val _vaccineCost = MutableStateFlow("")
    val vaccineCost: StateFlow<String> get() = _vaccineCost

    private val _vaccineDate = MutableStateFlow("")
    val vaccineDate: StateFlow<String> get() = _vaccineDate

    private val _supplier = MutableStateFlow("")
    val supplier: StateFlow<String> get() = _supplier

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onVaccineNameChange(newVaccineName: String) {
        _vaccineName.value = newVaccineName
    }

    fun onVaccineCostChange(newVaccineCost: String) {
        if (newVaccineCost.length < 16) {
            _vaccineCost.value = newVaccineCost
        }
    }

    fun onVaccineDateChange(newVaccineDate: String) {
        _vaccineDate.value = newVaccineDate
    }

    fun onSupplierChange(newSupplier: String) {
        _supplier.value = newSupplier
    }

    fun loadVaccineData(userKey: String, farmKey: String, cowKey: String, vaccineKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getSingleVaccineUseCase.getSingleVaccine(
                userKey,
                farmKey,
                cowKey,
                vaccineKey
            ) { vaccine ->
                if (vaccine != null) {
                    _vaccineName.value = vaccine.vaccineName
                    _vaccineCost.value = vaccine.vaccineCost.toString()
                    _vaccineDate.value = vaccine.date
                    _supplier.value = vaccine.supplier
                }
            }
            _uiState.value = UiState.Success
        }
    }

    fun onEditVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String,
        onRegisterDone: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            if (checkData()) {

                var cost: Double = 0.0
                if (_vaccineCost.value.isNotBlank()) {
                    cost = _vaccineCost.value.toDouble()
                }

                val newVaccine = Vaccine(
                    vaccineName = vaccineName.value,
                    vaccineCost = cost,
                    date = _vaccineDate.value,
                    supplier = _supplier.value
                )

                editVaccineUseCase.editVaccine(userKey, farmKey, cowKey, vaccineKey, newVaccine)

                delay(1000)
                _uiState.value = UiState.Idle
                onRegisterDone()
            } else {
                _uiState.value = UiState.Error("Debe llenar todos los campos")
            }
        }
    }

    private fun checkData(): Boolean {
        return (_vaccineName.value.isNotBlank()
                && _vaccineCost.value.isNotBlank()
                && _vaccineDate.value.isNotBlank()
                && _supplier.value.isNotBlank())
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}