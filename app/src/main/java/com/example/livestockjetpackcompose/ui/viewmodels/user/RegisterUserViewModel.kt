package com.example.livestockjetpackcompose.ui.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.User
import com.example.livestockjetpackcompose.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> get() = _phone

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> get() = _repeatPassword

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onPhoneChange(phone: String) {
        _phone.value = phone
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onRepeatPasswordChange(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
    }

    fun registerUser() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                if (_name.value.isBlank() || _phone.value.isBlank() || _password.value.isBlank() || _repeatPassword.value.isBlank()) {
                    _uiState.value = UiState.Error("Debe de llenar todos los campos")
                } else {
                    val user = User(createUserId(), _name.value, _password.value, _phone.value)
                    registerUserUseCase.invoke(user)
                    _uiState.value = UiState.Success
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.toString())
            }
        }
    }

    private fun createUserId(): String {
        return Calendar.getInstance().timeInMillis.hashCode().toString()
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}