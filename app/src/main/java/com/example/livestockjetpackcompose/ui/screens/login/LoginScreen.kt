package com.example.livestockjetpackcompose.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.ui.ButtonCustom
import com.example.livestockjetpackcompose.ui.ButtonType
import com.example.livestockjetpackcompose.ui.Footer
import com.example.livestockjetpackcompose.ui.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.TextFieldType
import com.example.livestockjetpackcompose.ui.Title
import com.example.livestockjetpackcompose.ui.theme.*

@Composable
fun LoginScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Welcome \n Sign In to continue")
        Body(Modifier.weight(3f))
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun Body(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextFieldCustom(TextFieldType.TEXT, "User")
        OutlinedTextFieldCustom(TextFieldType.PASSWORD, "Password")
        LoginScreenButtons()
    }
}

@Composable
private fun LoginScreenButtons() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 15.dp)
    ) {
        ButtonCustom(ButtonType.SPECIAL, "Login")
        ButtonCustom(ButtonType.SIMPLE, "Register")
        ButtonCustom(ButtonType.SIMPLE, "¿How to login?")
    }
}