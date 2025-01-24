package com.example.livestockjetpackcompose.ui.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.domain.usecase.GetUserSingleFarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getUserSingleFarmUseCase: GetUserSingleFarmUseCase
) : ViewModel() {

    private var _farm = MutableStateFlow<Farm?>(null)
    val farm: StateFlow<Farm?> get() = _farm

    fun getFarmData(userKey: String, farmKey: String) {
        viewModelScope.launch {
            getUserSingleFarmUseCase.getUserSingleFarm(userKey = userKey, farmKey = farmKey){ farm ->
                _farm.value = farm
            }
        }
    }
}