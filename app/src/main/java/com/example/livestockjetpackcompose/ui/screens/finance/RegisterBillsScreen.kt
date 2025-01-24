package com.example.livestockjetpackcompose.ui.screens.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
fun RegisterBillsScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Gastos")
        Body(Modifier.weight(4f))
        Buttons(Modifier.weight(1f))
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun Body(modifier: Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Nombre del producto",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Descripción del producto",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Fecha de la compra",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Precio del producto",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Nombre del proveedor",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Telefono del proveedor",
                    text = "",
                    onValueChange = {})
            }
        }
    )

}

@Composable
private fun Buttons(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Guardar"){

        }
    }
}
