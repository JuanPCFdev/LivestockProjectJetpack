package com.example.livestockjetpackcompose.ui.viewmodels.cows.lifting.weight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import com.example.livestockjetpackcompose.domain.usecase.RegisterNewLiftingPerformanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterWeightViewModel @Inject constructor(
    private val registerNewLiftingPerformanceUseCase: RegisterNewLiftingPerformanceUseCase
) : ViewModel() {

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> get() = _date

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> get() = _weight

    private val _diet = MutableStateFlow("")
    val diet: StateFlow<String> get() = _diet

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onDateChange(newDate: String) {
        _date.value = newDate
    }

    fun onWeightChange(newWeight: String) {
        _weight.value = newWeight
    }

    fun onDietChange(newDiet: String) {
        _diet.value = newDiet
    }

    fun onRegisterWeight(
        userKey: String,
        farmKey: String,
        cowKey: String,
        onRegisterDone: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            if (checkData()) {
                val newWeight = LiftingPerformance(
                    PLDate = _date.value,
                    PLWeight = _weight.value.toInt(),
                    PLDiet = _diet.value
                )
                registerNewLiftingPerformanceUseCase.registerNewLiftingPerformance(
                    userKey,
                    farmKey,
                    cowKey,
                    newWeight
                )
                delay(1500)
                _uiState.value = UiState.Success
                onRegisterDone()
            }else{
                _uiState.value = UiState.Error("Debe de llenar todos los campos")
            }
        }
    }

    private fun checkData(): Boolean {
        return (_date.value.isNotBlank() && _weight.value.isNotBlank() && _diet.value.isNotBlank())
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}