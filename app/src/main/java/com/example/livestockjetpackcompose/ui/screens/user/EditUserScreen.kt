package com.example.livestockjetpackcompose.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.viewmodels.user.EditUserViewModel

@Composable
fun EditUserScreen(
    modifier: Modifier,
    userKey: String,
    onEditUserDone: () -> Unit,
    viewModel: EditUserViewModel = hiltViewModel()
) {

    val name = viewModel.name.collectAsState()
    val phone = viewModel.phone.collectAsState()
    val password = viewModel.password.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userKey) {
        viewModel.getUserData(userKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Example User Name")
        Body(
            modifier = Modifier.weight(2f),
            name = name.value,
            phone = phone.value,
            password = password.value,
            onNameChange = { newName -> viewModel.onNameChange(newName) },
            onPhoneChange = { newPhone -> viewModel.onPhoneChange(newPhone) },
            onPasswordChange = { newPassword -> viewModel.onPasswordChange(newPassword) }
        )
        EditButton(
            modifier = Modifier.weight(1f),
            onEditDone = {
                viewModel.editUser(
                    userKey = userKey,
                    onEditUserDone = { onEditUserDone() }
                )
            }
        )

        when (uiState) {
            is EditUserViewModel.UiState.Loading -> CircularProgressIndicator()
            is EditUserViewModel.UiState.Error -> Text(
                text = (uiState as EditUserViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> Unit
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
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        OutlinedTextFieldCustom(
            TextFieldType.TEXT,
            placeHolder = "User name",
            text = name,
            onValueChange = onNameChange
        )

        OutlinedTextFieldCustom(
            TextFieldType.TEXT,
            placeHolder = "User phone",
            text = phone,
            onValueChange = onPhoneChange
        )

        OutlinedTextFieldCustom(
            TextFieldType.TEXT,
            placeHolder = "Password",
            text = password,
            onValueChange = onPasswordChange
        )
    }
}

@Composable
private fun EditButton(modifier: Modifier, onEditDone: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            ButtonCustom(ButtonType.SPECIAL, "Save Changes") {
                onEditDone()
            }
            ButtonCustom(ButtonType.DANGER, "Delete User") {
                //Ultima implementación de la app
            }
        }
    }
}