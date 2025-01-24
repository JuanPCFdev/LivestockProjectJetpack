package com.example.livestockjetpackcompose.ui.screens.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
fun SellCowScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Venta de ganado")
        BodySellCow(Modifier.weight(4f))
        SaveChangesButton(Modifier.weight(1f))
    }
}

@Composable
private fun BodySellCow(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 8.dp)
                .background(background_app)
        ) {
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Marcación",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Peso de venta",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Precio de venta",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Nombre de comprador",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Telefono del comprador",
                    text = "",
                    onValueChange = {})
            }
        }
    }
}

@Composable
private fun SaveChangesButton(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Guardar recibo de venta"){

        }
    }
}