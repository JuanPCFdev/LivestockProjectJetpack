package com.example.livestockjetpackcompose.ui.viewmodels.farm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.usecase.RegisterFarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFarmViewModel @Inject constructor(
    private val registerFarmUseCase: RegisterFarmUseCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _hectares = MutableStateFlow("")
    val hectares: StateFlow<String> get() = _hectares

    private val _capacity = MutableStateFlow("")
    val capacity: StateFlow<String> get() = _capacity

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onAddressChange(address: String) {
        _address.value = address
    }

    fun onHectaresChange(hectares: String) {
        _hectares.value = hectares
    }

    fun onCapacityChange(capacity: String) {
        _capacity.value = capacity
    }

    fun registerFarm(
        userKey: String,
        viewModel: ListFarmViewModel,
        onRegisterFarmDone: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                if (!validateFields() && userKey.isNotBlank()) {
                    val farm = Farm(
                        nameFarm = _name.value,
                        hectares = _hectares.value.toDouble(),
                        numCows = _capacity.value.toInt(),
                        address = _address.value
                    )
                    registerFarmUseCase.invoke(userKey, farm)
                    _uiState.value = UiState.Loading

                    delay(1000)

                    _uiState.value = UiState.Success
                    viewModel.loadUserFarms(userKey)
                    onRegisterFarmDone()
                } else {
                    _uiState.value = UiState.Error("Todos los campos deben ser llenados")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.toString())
            }

        }
    }

    private fun validateFields(): Boolean {
        return (_name.value.isBlank() || _address.value.isBlank() || _capacity.value.isBlank() || _hectares.value.isBlank())
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }


}