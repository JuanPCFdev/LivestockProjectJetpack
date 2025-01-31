package com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Insemination
import com.example.livestockjetpackcompose.domain.usecase.RegisterNewInseminationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterInseminationViewModel @Inject constructor(
    private val registerNewInseminationUseCase: RegisterNewInseminationUseCase
) : ViewModel() {

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> get() = _date

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    private val _spermOrigin = MutableStateFlow("")
    val spermOrigin: StateFlow<String> get() = _spermOrigin

    fun onDateChange(newDate: String) {
        _date.value = newDate
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun onSpermOriginChange(newSpermOrigin: String) {
        _spermOrigin.value = newSpermOrigin
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onRegisterInsemination(
        userKey: String,
        farmKey: String,
        cowKey: String,
        onRegisterDone: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            if (checkData()) {
                val newInsemination = Insemination(
                    inseminationDate = _date.value,
                    descOfInsemination = _description.value,
                    spermOrigin = _spermOrigin.value
                )

                registerNewInseminationUseCase.registerNewInsemination(
                    userKey,
                    farmKey,
                    cowKey,
                    newInsemination
                )
                delay(1000)
                _uiState.value = UiState.Success
                onRegisterDone()
            } else {
                _uiState.value = UiState.Error("Debe de llenar todos los campos")
            }

        }
    }

    private fun checkData(): Boolean {
        return (_date.value.isNotBlank() && _description.value.isNotBlank() && _spermOrigin.value.isNotBlank())
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}