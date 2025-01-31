package com.example.livestockjetpackcompose.ui.viewmodels.cows.lifting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.LiftingPerformance
import com.example.livestockjetpackcompose.domain.usecase.DeleteLiftingPerformanceUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetAllLiftingPerformanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiftingStatsViewModel @Inject constructor(
    private val getAllLiftingPerformanceUseCase: GetAllLiftingPerformanceUseCase,
    private val deleteLiftingPerformanceUseCase: DeleteLiftingPerformanceUseCase
) : ViewModel() {

    private val _liftingList = MutableStateFlow<List<LiftingPerformance>?>(null)
    val liftingList: StateFlow<List<LiftingPerformance>?> get() = _liftingList

    private val _keys = MutableStateFlow<List<String>?>(null)
    val keys: StateFlow<List<String>?> get() = _keys

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadLiftingPerformanceData(userKey: String, farmKey: String, cowKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getAllLiftingPerformanceUseCase.getAllLiftingPerformance(
                userKey,
                farmKey,
                cowKey
            ) { plData, plKeys ->
                if (plData != null && plKeys != null) {
                    val combinedList = plData.zip(plKeys)

                    val sortedCombinedList = combinedList.sortedBy { it.first.PLDate }

                    val sortedLiftingList = sortedCombinedList.map { it.first }
                    val sortedKeys = sortedCombinedList.map { it.second }

                    _liftingList.value = sortedLiftingList
                    _keys.value = sortedKeys
                    _uiState.value = UiState.Success
                } else {
                    _uiState.value = UiState.Error("No se ha logrado cargar los datos de la vaca")
                }
            }
        }
    }

    fun deleteLiftingPerformance(
        userKey: String,
        farmKey: String,
        cowKey: String,
        liftingKey: String
    ) {
        viewModelScope.launch {
            if (userKey.isNotBlank() && farmKey.isNotBlank() && cowKey.isNotBlank() && liftingKey.isNotBlank()) {
                _uiState.value = UiState.Loading

                deleteLiftingPerformanceUseCase.deleteLiftingPerformance(
                    userKey,
                    farmKey,
                    cowKey,
                    liftingKey
                )

                val currentLiftingList = _liftingList.value
                val currentKeys = _keys.value

                if (currentLiftingList != null && currentKeys != null) {
                    val indexToRemove = currentKeys.indexOf(liftingKey)

                    if (indexToRemove != -1) {
                        val updatedLiftingList =
                            currentLiftingList.toMutableList().apply { removeAt(indexToRemove) }
                        val updatedKeys =
                            currentKeys.toMutableList().apply { removeAt(indexToRemove) }

                        _liftingList.value = updatedLiftingList
                        _keys.value = updatedKeys
                    }
                }
                delay(2000)
                loadLiftingPerformanceData(userKey, farmKey, cowKey)
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