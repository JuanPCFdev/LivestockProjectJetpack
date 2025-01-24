package com.example.livestockjetpackcompose.ui.viewmodels.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livestockjetpackcompose.domain.model.User
import com.example.livestockjetpackcompose.domain.usecase.EditUserUseCase
import com.example.livestockjetpackcompose.domain.usecase.GetUserDataFromKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel @Inject constructor(
    private val getUserDataFromKeyUseCase: GetUserDataFromKeyUseCase,
    private val editUserUseCase: EditUserUseCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> get() = _phone

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onPhoneChange(newPhone: String) {
        _phone.value = newPhone
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun getUserData(userKey: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getUserDataFromKeyUseCase.getUserData(userKey) { user ->
                _name.value = user?.name ?: ""
                _phone.value = user?.phone ?: ""
                _password.value = user?.password ?: ""
            }
            _uiState.value = UiState.Idle
        }
    }

    fun editUser(userKey: String, onEditUserDone: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val newUser = User(
                name = _name.value,
                phone = _phone.value,
                password = _password.value
            )

            editUserUseCase.editUser(userKey, newUser)

            delay(1000)

            onEditUserDone()

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