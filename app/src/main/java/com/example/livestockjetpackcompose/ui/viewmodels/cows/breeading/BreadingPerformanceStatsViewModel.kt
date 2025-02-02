package com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import com.example.livestockjetpackcompose.domain.usecase.DeleteBreadingPerformanceUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetAllBreadingPerformanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreadingPerformanceStatsViewModel @Inject constructor(
    private val getAllBreadingPerformanceUseCase: GetAllBreadingPerformanceUseCase,
    private val deleteBreadingPerformanceUseCase: DeleteBreadingPerformanceUseCase
) : ViewModel() {

    private val _pBreadingList = MutableStateFlow<List<BreedingPerformance>?>(null)
    val pBeadingList: StateFlow<List<BreedingPerformance>?> get() = _pBreadingList

    private val _keys = MutableStateFlow<List<String>?>(null)
    val keys: StateFlow<List<String>?> get() = _keys

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadBreadingPerformanceData(userKey: String, farmKey: String, cowKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getAllBreadingPerformanceUseCase.getAllBreeadingPerformance(
                userKey,
                farmKey,
                cowKey
            ) { pBreading, keys ->
                if (pBreading != null && keys != null) {
                    val mixList = pBreading.zip(keys)

                    val sortedCombinedList = mixList.sortedBy { it.first.PBDate }

                    val sortedPBreadingList = sortedCombinedList.map { it.first }
                    val sortedKeys = sortedCombinedList.map { it.second }

                    _pBreadingList.value = sortedPBreadingList
                    _keys.value = sortedKeys
                    _uiState.value = UiState.Success
                } else {
                    _uiState.value = UiState.Error("No se ha logrado cargar los datos de la vaca")
                }
            }

        }
    }

    fun deleteBreadingPerformanceData(
        userKey: String,
        farmKey: String,
        cowKey: String,
        pbKey: String
    ) {
        viewModelScope.launch {
            if (userKey.isNotBlank() && farmKey.isNotBlank() && cowKey.isNotBlank() && pbKey.isNotBlank()) {
                _uiState.value = UiState.Loading
                deleteBreadingPerformanceUseCase.deleteBreeadingPerformance(
                    userKey,
                    farmKey,
                    cowKey,
                    pbKey
                )
                val currentBreadingPerformanceList = _pBreadingList.value
                val currentKeys = _keys.value

                if (currentBreadingPerformanceList != null && currentKeys != null) {
                    val indexToRemove = currentKeys.indexOf(pbKey)

                    if (indexToRemove != -1) {
                        val updatedInseminationList =
                            currentBreadingPerformanceList.toMutableList()
                                .apply { removeAt(indexToRemove) }
                        val updatedKeys =
                            currentKeys.toMutableList().apply { removeAt(indexToRemove) }

                        _pBreadingList.value = updatedInseminationList
                        _keys.value = updatedKeys
                    }
                }
                delay(2000)
                loadBreadingPerformanceData(userKey, farmKey, cowKey)
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