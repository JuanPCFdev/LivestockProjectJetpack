package com.example.livestockjetpackcompose.ui.viewmodels.farm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.usecase.EditFarmUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetUserSingleFarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditFarmViewModel @Inject constructor(
    private val getUserSingleFarmUseCase: GetUserSingleFarmUseCase,
    private val editFarmUseCase: EditFarmUseCase
) : ViewModel() {

    private val _farmName = MutableStateFlow<String>("")
    val farmName: StateFlow<String> get() = _farmName

    private val _farmAddress = MutableStateFlow<String>("")
    val farmAddress: StateFlow<String> get() = _farmAddress

    private val _farmHectares = MutableStateFlow<String>("")
    val farmHectares: StateFlow<String> get() = _farmHectares

    private val _farmCapacity = MutableStateFlow<String>("")
    val farmCapacity: StateFlow<String> get() = _farmCapacity

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onFarmNameChange(newFarmName: String) {
        _farmName.value = newFarmName
    }

    fun onFarmAddressChange(newFarmAddress: String) {
        _farmAddress.value = newFarmAddress
    }

    fun onFarmHectaresChange(newFarmHectares: String) {
        _farmHectares.value = newFarmHectares
    }

    fun onFarmCapacityChange(newFarmCapacity: String) {
        _farmCapacity.value = newFarmCapacity
    }

    fun getFarmData(userKey: String, farmKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getUserSingleFarmUseCase.getUserSingleFarm(userKey, farmKey) { farmDataCallback ->
                _farmName.value = farmDataCallback?.nameFarm ?: ""
                _farmAddress.value = farmDataCallback?.address ?: ""
                _farmHectares.value = farmDataCallback?.hectares.toString() ?: ""
                _farmCapacity.value = farmDataCallback?.numCows.toString() ?: ""
            }
            _uiState.value = UiState.Idle
        }
    }

    fun editFarm(userKey: String, farmKey: String, onEditFarmDone: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val newFarm = Farm(
                nameFarm = _farmName.value,
                hectares = _farmHectares.value.toDouble(),
                numCows = _farmCapacity.value.toInt(),
                address = _farmAddress.value
            )
            editFarmUseCase.editFarm(newFarm, userKey, farmKey)
            delay(1000)
            onEditFarmDone()
            _uiState.value = UiState.Idle
        }
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}