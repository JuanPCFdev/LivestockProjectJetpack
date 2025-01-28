package com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Vaccine
import com.example.livestockjetpackcompose.domain.usecase.getCowVaccinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VaccineListViewModel @Inject constructor(
    private val getCowVaccinesUseCase: getCowVaccinesUseCase
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

            getCowVaccinesUseCase.getCowVaccines(userKey, farmKey, cowKey){ vaccinesList, keysList ->
                if(vaccinesList != null && keysList != null){
                    _vaccines.value = vaccinesList
                    _keys.value = keysList
                }else{
                    Log.e("VaccineList", "Error al cargar la data de las vacunas")
                }
            }

            _uiState.value = UiState.Idle
        }
    }

    fun deleteSelectedVaccine(userKey: String, farmKey: String, cowKey: String, vaccineKey:String){
        viewModelScope.launch {
            if (userKey.isNotBlank() && farmKey.isNotBlank() && cowKey.isNotBlank() && vaccineKey.isNotBlank()){
                _uiState.value = UiState.Loading

                delay(2000)
                loadVaccines(userKey, farmKey, cowKey)
                _uiState.value = UiState.Idle
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