package com.example.livestockjetpackcompose.ui.screens.cows.breeading.born

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun RegisterBornCowScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Registrar Parto")
        BodyRegisterBornCow(Modifier.weight(4f))
        SaveChangesButton(Modifier.weight(1f))
    }
}

@Composable
private fun BodyRegisterBornCow(modifier: Modifier) {
    val checkedState1 = remember { mutableStateOf(false) }
    val checkedState2 = remember { mutableStateOf(false) }
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
                    TextFieldType.TEXT, "Fecha de Nacimiento",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Peso",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Edad",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Raza",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Estado",
                    text = "",
                    onValueChange = {})
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Sexo",
                    text = "",
                    onValueChange = {})
            }

            item {
                CheckQuestion(
                    text = "¿La cría nacío enferma?",
                    checkedState = checkedState1.value,
                    onCheckedChange = {
                        checkedState1.value = it
                    }
                )
            }

            item {
                CheckQuestion(
                    text = "¿La cría murío?",
                    checkedState = checkedState2.value,
                    onCheckedChange = {
                        checkedState2.value = it
                    }
                )
            }
        }
    }
}

@Composable
private fun CheckQuestion(text: String, checkedState: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text)
            Checkbox(
                checked = checkedState,
                onCheckedChange = { onCheckedChange(it) },
                enabled = true
            )
        }
    }
}

@Composable
private fun SaveChangesButton(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, " Guardar Cambios"){

        }
    }
}