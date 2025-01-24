package com.example.livestockjetpackcompose.ui.screens.cows.vaccine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun EditDeleteVaccineScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Editar o Eliminar Vacuna")
        CardBody(Modifier.weight(4f))
        ButtonsVaccine(Modifier.weight(2f))
    }
}

@Composable
private fun CardBody(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxSize().padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Nombre de la Vacuna",
                text = "",
                onValueChange = {})
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Valor de la Vacuna",
                text = "",
                onValueChange = {})
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Fecha de Suministración",
                text = "",
                onValueChange = {})
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Suministrador",
                text = "",
                onValueChange = {})
        }
    }
}

@Composable
private fun ButtonsVaccine(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ButtonCustom(ButtonType.SPECIAL, "Guardar Cambios"){

        }
        ButtonCustom(ButtonType.DANGER, "Eliminar Vacuna"){

        }
    }
}