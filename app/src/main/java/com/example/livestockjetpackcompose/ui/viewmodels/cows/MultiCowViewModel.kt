package com.example.livestockjetpackcompose.ui.viewmodels.cows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.usecase.DeleteCowUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetUserFarmCowsUseCase
import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MultiCowViewModel @Inject constructor(
    private val getUserFarmCowsUseCase: GetUserFarmCowsUseCase,
    private val deleteCowUseCase: DeleteCowUseCase
) : ViewModel() {

    private val _cows = MutableStateFlow<List<Cattle>?>(null)
    val cows: StateFlow<List<Cattle>?> get() = _cows

    private val _cowsKeys = MutableStateFlow<List<String>?>(null)
    val cowsKeys: StateFlow<List<String>?> get() = _cowsKeys

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadUserFarmCows(userKey: String, farmKey: String, cowFilter: CowTypeFilter) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getUserFarmCowsUseCase.getUserFarmCows(userKey, farmKey) { cowsData, cowsKeysData ->
                if (cowsData != null && cowsKeysData != null) {

                    when (cowFilter) {
                        CowTypeFilter.LIFTING -> {
                            val combinedList = cowsData.zip(cowsKeysData)
                            val filteredList = combinedList.filter { it.first.type == "lifting" }

                            val filteredCows = filteredList.map { it.first }
                            val filteredCowsKeys = filteredList.map { it.second }

                            _cows.value = filteredCows
                            _cowsKeys.value = filteredCowsKeys
                        }

                        CowTypeFilter.BREEADING -> {
                            val combinedList = cowsData.zip(cowsKeysData)
                            val filteredList = combinedList.filter { it.first.type == "breeading" }

                            val filteredCows = filteredList.map { it.first }
                            val filteredCowsKeys = filteredList.map { it.second }

                            _cows.value = filteredCows
                            _cowsKeys.value = filteredCowsKeys
                        }

                        CowTypeFilter.CORRAL -> {
                            val combinedList = cowsData.zip(cowsKeysData)
                            val filteredList = combinedList.filter { it.first.type == "corral" }

                            val filteredCows = filteredList.map { it.first }
                            val filteredCowsKeys = filteredList.map { it.second }

                            _cows.value = filteredCows
                            _cowsKeys.value = filteredCowsKeys
                        }

                        CowTypeFilter.DEAD -> {
                            val combinedList = cowsData.zip(cowsKeysData)
                            val filteredList = combinedList.filter { it.first.type == "dead" }

                            val filteredCows = filteredList.map { it.first }
                            val filteredCowsKeys = filteredList.map { it.second }

                            _cows.value = filteredCows
                            _cowsKeys.value = filteredCowsKeys
                        }

                        CowTypeFilter.SOLD -> {
                            val combinedList = cowsData.zip(cowsKeysData)
                            val filteredList = combinedList.filter { it.first.type == "sold" }

                            val filteredCows = filteredList.map { it.first }
                            val filteredCowsKeys = filteredList.map { it.second }

                            _cows.value = filteredCows
                            _cowsKeys.value = filteredCowsKeys
                        }
                    }

                } else {
                    Log.e("MultiCowViewModel", "Error al cargar la data de las vacas")
                }
            }
            _uiState.value = UiState.Idle
        }
    }

    fun deleteSelectedCow(userKey: String, farmKey: String, cowKey:String, cowFilter: CowTypeFilter){
        viewModelScope.launch {
            if (userKey.isNotBlank() && farmKey.isNotBlank() && cowKey.isNotBlank()){
                _uiState.value = UiState.Loading
                deleteCowUseCase.deleteCow(userKey, farmKey, cowKey)
                delay(2000)
                _uiState.value = UiState.Idle
                loadUserFarmCows(userKey,farmKey, cowFilter)
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