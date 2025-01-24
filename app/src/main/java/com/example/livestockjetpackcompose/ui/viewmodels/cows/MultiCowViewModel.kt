package com.example.livestockjetpackcompose.ui.viewmodels.cows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.usecase.GetUserFarmCowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiCowViewModel @Inject constructor(
    private val getUserFarmCowsUseCase: GetUserFarmCowsUseCase
) : ViewModel() {

    private val _cows = MutableStateFlow<List<Cattle>?>(null)
    val cows: StateFlow<List<Cattle>?> get() = _cows

    private val _cowsKeys = MutableStateFlow<List<String>?>(null)
    val cowsKeys: StateFlow<List<String>?> get() = _cowsKeys

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadUserFarmCows(userKey: String, farmKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getUserFarmCowsUseCase.getUserFarmCows(userKey, farmKey) { cowsData, cowsKeysData ->
                if (cowsData != null && cowsKeysData != null) {
                    _cows.value = cowsData
                    _cowsKeys.value = cowsKeysData
                } else {
                    Log.e("MultiCowViewModel", "Error al cargar la data de las vacas")
                }
            }
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