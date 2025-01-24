package com.example.livestockjetpackcompose.ui.screens.login

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
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.login.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier,
    navigateToRegisterFarm: (userKey: String) -> Unit,
    navigateToRegisterUser: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "FincaBox")
        Body(
            modifier = Modifier.weight(3f),
            navigateToRegisterUser = { navigateToRegisterUser() },
            username = username,
            password = password,
            onUsernameChange = { newUsername -> viewModel.onUsernameChange(newUsername) },
            onPasswordChange = { newPassword -> viewModel.onPasswordChange(newPassword) },
            onLogin = {
                viewModel.login { user ->
                    navigateToRegisterFarm(
                        user.first
                    )
                }
            },
            uiState = uiState
        )
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun Body(
    modifier: Modifier,
    navigateToRegisterUser: () -> Unit,
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    uiState: LoginViewModel.UiState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextFieldCustom(
            TextFieldType.TEXT,
            "Usuario",
            text = username,
            onValueChange = onUsernameChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.PASSWORD,
            "Contraseña",
            text = password,
            onValueChange = onPasswordChange
        )
        LoginScreenButtons(
            onLogin = { onLogin() },
            navigateToRegisterUser = { navigateToRegisterUser() }
        )

        when (uiState) {
            is LoginViewModel.UiState.Loading -> CircularProgressIndicator()
            is LoginViewModel.UiState.Error -> Text(
                text = uiState.message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> Unit
        }
    }
}

@Composable
private fun LoginScreenButtons(
    onLogin: () -> Unit,
    navigateToRegisterUser: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 15.dp)
    ) {
        ButtonCustom(ButtonType.SPECIAL, "Ingresar") {
            onLogin()
        }
        ButtonCustom(ButtonType.SIMPLE, "Registrarse") {
            navigateToRegisterUser()
        }
        ButtonCustom(ButtonType.SIMPLE, "¿Como registrarse?") {

        }
    }
}