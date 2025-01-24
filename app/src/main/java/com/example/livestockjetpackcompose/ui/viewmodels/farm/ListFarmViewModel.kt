package com.example.livestockjetpackcompose.ui.viewmodels.farm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.usecase.DeleteFarmUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetUserFarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFarmViewModel @Inject constructor(
    private val getUserFarmsUseCase: GetUserFarmsUseCase,
    private val deleteFarmUseCase: DeleteFarmUseCase
) : ViewModel() {

    private var _farms = MutableStateFlow<List<Farm>?>(null)
    val farms: StateFlow<List<Farm>?> get() = _farms

    private var _farmsKeys = MutableStateFlow<List<String>?>(null)
    val farmsKeys: StateFlow<List<String>?> get() = _farmsKeys

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState


    fun loadUserFarms(userKey: String) {
        viewModelScope.launch {
            getUserFarmsUseCase.getUserFarms(userKey) { farms, farmKeys ->
                if (farms != null && farmKeys != null) {
                    _farms.value = farms
                    _farmsKeys.value = farmKeys
                } else {
                    Log.e("FarmViewModel", "Error al cargar la data de las fincas")
                }
            }
        }
    }

    fun deleteFarm(userKey: String, farmKey: String) {
        viewModelScope.launch {
            if (userKey.isNotBlank() && farmKey.isNotBlank()){
                _uiState.value = UiState.Loading
                deleteFarmUseCase.deleteFarm(userKey, farmKey)
                delay(2000)
                _uiState.value = UiState.Success
                loadUserFarms(userKey)
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