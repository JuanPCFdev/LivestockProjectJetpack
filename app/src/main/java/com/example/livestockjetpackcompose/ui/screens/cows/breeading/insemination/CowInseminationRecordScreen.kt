package com.example.livestockjetpackcompose.ui.screens.cows.breeading.insemination

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun CowInseminationRecordScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Registrar Monta")
        CardRegisterInsemination(Modifier.weight(3f))
        ButtonRegisterInsemination(Modifier.weight(1f))
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun CardRegisterInsemination(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Fecha de Inseminación",
                text = "",
                onValueChange = {})
            OutlinedTextFieldCustom(
                TextFieldType.NUMBER, "Descripción",
                text = "",
                onValueChange = {})
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Procedencia del esperma",
                text = "",
                onValueChange = {})
        }
    }
}

@Composable
private fun ButtonRegisterInsemination(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar"){

        }
    }
}