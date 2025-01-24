package com.example.livestockjetpackcompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.data.repositories.UserRepository
import com.example.livestockjetpackcompose.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    //State for the username and password fields
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    //State for UI feedback
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    //Update functions for the text fields
    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login(navigateToRegisterFarm:()->Unit) {
        viewModelScope.launch {

            if (_username.value.isBlank() || _password.value.isBlank()) {
                _uiState.value = UiState.Error("Los campos están vacíos")
                return@launch
            }

            var user: User? = null

            _uiState.value = UiState.Loading

            onLoginUser { userFound ->
                user = userFound
            }

            //Mocked login validation
            if ( user!=null ) {
                _uiState.value = UiState.Success
                navigateToRegisterFarm()//Enviar el usuario para la finca
            } else {
                _uiState.value = UiState.Error("Credenciales no válidas")
            }
        }
    }

    fun onLoginUser(onSuccess: (User?) -> Unit){
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = userRepository.validateCredentials(_username.value, _password.value)

            if (result.isSuccess) {
                val user = result.getOrNull()!!
                _uiState.value = UiState.Success
                onSuccess(user)
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Credenciales no válidas"
                _uiState.value = UiState.Error(errorMessage)
                onSuccess(null)
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}