package com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Vaccine
import com.example.livestockjetpackcompose.domain.usecase.DeleteVaccineUseCase
import com.example.livestockjetpackcompose.domain.usecase.getCowVaccinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaccineListViewModel @Inject constructor(
    private val getCowVaccinesUseCase: getCowVaccinesUseCase,
    private val deleteVaccineUseCase: DeleteVaccineUseCase
) : ViewModel() {

    private val _vaccines = MutableStateFlow<List<Vaccine>?>(null)
    val vaccines: StateFlow<List<Vaccine>?> get() = _vaccines

    private val _keys = MutableStateFlow<List<String>?>(null)
    val keys: StateFlow<List<String>?> get() = _keys

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadVaccines(userKey: String, farmKey: String, cowKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getCowVaccinesUseCase.getCowVaccines(
                userKey,
                farmKey,
                cowKey
            ) { vaccinesList, keysList ->
                if (vaccinesList != null && keysList != null) {
                    _vaccines.value = vaccinesList
                    _keys.value = keysList
                    _uiState.value = UiState.Success
                } else {
                    _uiState.value = UiState.Error("Error al cargar las vacunas")
                }
            }
        }
    }

    fun deleteSelectedVaccine(
        userKey: String,
        farmKey: String,
        cowKey: String,
        vaccineKey: String
    ) {
        viewModelScope.launch {
            if (userKey.isNotBlank() && farmKey.isNotBlank() && cowKey.isNotBlank() && vaccineKey.isNotBlank()) {
                _uiState.value = UiState.Loading

                // Elimina la vacuna en la base de datos
                deleteVaccineUseCase.deleteVaccine(userKey, farmKey, cowKey, vaccineKey)

                // Actualiza la lista localmente
                val currentVaccines = _vaccines.value
                val currentKeys = _keys.value

                if (currentVaccines != null && currentKeys != null) {
                    // Encuentra el índice de la vacuna a eliminar
                    val indexToRemove = currentKeys.indexOf(vaccineKey)

                    if (indexToRemove != -1) {
                        // Filtra las listas para eliminar la vacuna con la key correspondiente
                        val updatedVaccines = currentVaccines.toMutableList().apply { removeAt(indexToRemove) }
                        val updatedKeys = currentKeys.toMutableList().apply { removeAt(indexToRemove) }

                        // Actualiza los StateFlow
                        _vaccines.value = updatedVaccines
                        _keys.value = updatedKeys
                    }
                }
                delay(2000)
                loadVaccines(userKey, farmKey, cowKey)
                _uiState.value = UiState.Success
            }
        }
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}