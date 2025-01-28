package com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine

import androidx.lifecycle.ViewModel
import com.example.livestockjetpackcompose.domain.usecase.RegisterVaccineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterVaccineViewModel @Inject constructor(
    private val registerVaccineUseCase: RegisterVaccineUseCase
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
        _vaccineCost.value = newVaccineCost
    }

    fun onVaccineDateChange(newVaccineDate: String) {
        _vaccineDate.value = newVaccineDate
    }

    fun onSupplierChange(newSupplier: String) {
        _supplier.value = newSupplier
    }

    fun onRegisterVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        onRegisterDone: () -> Unit
    ) {

    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}