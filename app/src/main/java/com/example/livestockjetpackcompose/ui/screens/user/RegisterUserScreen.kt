package com.example.livestockjetpackcompose.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.viewmodels.user.RegisterUserViewModel

@Composable
fun RegisterUserScreen(
    modifier: Modifier,
    onRegisterUserDone: () -> Unit,
    viewModel: RegisterUserViewModel = hiltViewModel()
) {

    val name by viewModel.name.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val password by viewModel.password.collectAsState()
    val repeatPassword by viewModel.repeatPassword.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Register")
        Body(
            modifier = Modifier.weight(4f),
            name = name,
            phone = phone,
            password = password,
            repeatPassword = repeatPassword,
            onNameChange = { newName -> viewModel.onNameChange(newName) },
            onPhoneChange = { newPhone -> viewModel.onPhoneChange(newPhone) },
            onPasswordChange = { newPassword -> viewModel.onPasswordChange(newPassword) },
            onRepeatPasswordChange = { newRepeatPassword ->
                viewModel.onRepeatPasswordChange(
                    newRepeatPassword
                )
            }
        )

        when (uiState) {
            is RegisterUserViewModel.UiState.Error -> Text(
                text = (uiState as RegisterUserViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )

            RegisterUserViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }

        RegisterUserButtons(Modifier.weight(2f)) {
            viewModel.registerUser()
            onRegisterUserDone()
        }
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun Body(
    modifier: Modifier,
    name: String,
    phone: String,
    password: String,
    repeatPassword: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextFieldCustom(
            TextFieldType.TEXT, "Name",
            text = name,
            onValueChange = onNameChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.NUMBER, "Phone",
            text = phone,
            onValueChange = onPhoneChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.PASSWORD, "Password",
            text = password,
            onValueChange = onPasswordChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.PASSWORD, "ConfirmPassword",
            text = repeatPassword,
            onValueChange = onRepeatPasswordChange
        )
    }
}

@Composable
private fun RegisterUserButtons(modifier: Modifier, onRegisterUserPress: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(vertical = 15.dp)
    ) {
        ButtonCustom(ButtonType.SPECIAL, "Register") {
            onRegisterUserPress()
        }
    }
}