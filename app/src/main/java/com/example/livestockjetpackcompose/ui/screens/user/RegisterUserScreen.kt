package com.example.livestockjetpackcompose.ui.screens.user

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
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun RegisterUserScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Register")
        Body(Modifier.weight(4f))
        RegisterUserButtons(Modifier.weight(2f))
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun Body(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextFieldCustom(TextFieldType.TEXT,"Name")
        OutlinedTextFieldCustom(TextFieldType.NUMBER,"Phone")
        OutlinedTextFieldCustom(TextFieldType.PASSWORD,"Password")
        OutlinedTextFieldCustom(TextFieldType.PASSWORD,"ConfirmPassword")
    }
}

@Composable
private fun RegisterUserButtons(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(vertical = 15.dp)
    ) {
        ButtonCustom(ButtonType.SIMPLE,"I'm already registered")
        ButtonCustom(ButtonType.SPECIAL,"Register")
    }
}