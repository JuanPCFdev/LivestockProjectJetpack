package com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Insemination
import com.example.livestockjetpackcompose.domain.usecase.DeleteInseminationUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetAllInseminationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InseminationStatsViewModel @Inject constructor(
    private val getAllInseminationUseCase: GetAllInseminationUseCase,
    private val deleteInseminationUseCase: DeleteInseminationUseCase
) : ViewModel() {

    private val _inseminationList = MutableStateFlow<List<Insemination>?>(null)
    val inseminationList:StateFlow<List<Insemination>?> get() = _inseminationList

    private val _keys = MutableStateFlow<List<String>?>(null)
    val keys: StateFlow<List<String>?> get() = _keys

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun loadInseminationData(userKey:String, farmKey:String, cowKey:String){
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getAllInseminationUseCase.getAllInsemination(userKey, farmKey, cowKey){ inseminationData, keysData ->
                if(inseminationData != null && keysData != null){
                    val mixList = inseminationData.zip(keysData)

                    val sortedCombinedList = mixList.sortedBy { it.first.inseminationDate }

                    val sortedLiftingList = sortedCombinedList.map { it.first }
                    val sortedKeys = sortedCombinedList.map { it.second }

                    _inseminationList.value = sortedLiftingList
                    _keys.value = sortedKeys
                    _uiState.value = UiState.Success
                }else{
                    _uiState.value = UiState.Error("No se ha logrado cargar los datos de la vaca")
                }
            }
        }
    }

    fun deleteInsemination(userKey:String, farmKey: String, cowKey: String, inseminationKey:String){
        viewModelScope.launch {
            if (userKey.isNotBlank() && farmKey.isNotBlank() && cowKey.isNotBlank() && inseminationKey.isNotBlank()) {
                _uiState.value = UiState.Loading

                deleteInseminationUseCase.deleteInsemination(
                    userKey,
                    farmKey,
                    cowKey,
                    inseminationKey
                )

                val currentInseminationList = _inseminationList.value
                val currentKeys = _keys.value

                if (currentInseminationList != null && currentKeys != null) {
                    val indexToRemove = currentKeys.indexOf(inseminationKey)

                    if (indexToRemove != -1) {
                        val updatedInseminationList =
                            currentInseminationList.toMutableList().apply { removeAt(indexToRemove) }
                        val updatedKeys =
                            currentKeys.toMutableList().apply { removeAt(indexToRemove) }

                        _inseminationList.value = updatedInseminationList
                        _keys.value = updatedKeys
                    }
                }
                delay(1000)
                loadInseminationData(userKey, farmKey, cowKey)
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