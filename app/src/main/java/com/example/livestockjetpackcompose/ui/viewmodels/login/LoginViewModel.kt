package com.example.livestockjetpackcompose.ui.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.User
import com.example.livestockjetpackcompose.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login(navigateToFarm: (Pair<String, User>) -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {

                val user = loginUseCase.login(_username.value, _password.value)

                if (user != null) {
                    _uiState.value = UiState.Success
                    navigateToFarm(user)
                } else {
                    _uiState.value = UiState.Error("Credenciales invalidas")
                }

            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.toString())
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