package com.example.livestockjetpackcompose.ui.screens.cows

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
fun EditDeleteCowScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(0.7f), "Editar Ganado")
        CardFieldsTextEditDeleteCow(Modifier.weight(4f))
        ButtonsForEditCow(Modifier.weight(2f))
    }
}

@Composable
private fun CardFieldsTextEditDeleteCow(modifier: Modifier) {
    //true == cria     false == levante
    val whatHappenWithYourCow = false

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
                    TextFieldType.TEXT,
                    "Marcación",
                    text = "",
                    onValueChange = {}
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT,
                    "Fecha de Nacimiento",
                    text = "",
                    onValueChange = {}
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER,
                    "Peso",
                    text = "",
                    onValueChange = {}
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER,
                    "Edad",
                    text = "",
                    onValueChange = {}
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "R" +
                            "aza",
                    text = "",
                    onValueChange = {}
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT,
                    "Estado",
                    text = "",
                    onValueChange = {}
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT,
                    "Sexo",
                    text = "",
                    onValueChange = {}
                )
            }

            item {
                if (whatHappenWithYourCow) {
                    OutlinedTextFieldCustom(
                        TextFieldType.TEXT,
                        "Marcación Madre",
                        text = "",
                        onValueChange = {}
                    )
                    OutlinedTextFieldCustom(
                        TextFieldType.TEXT,
                        "Marcación Padre",
                        text = "",
                        onValueChange = {}
                    )
                } else {
                    CheckBoxCastratedCow()
                }
            }
        }
    }
}

@Composable
private fun CheckBoxCastratedCow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        val checkedState = remember { mutableStateOf(false) }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("¿La vaca ha sido castrada?")
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                enabled = true
            )
        }
    }
}

@Composable
private fun ButtonsForEditCow(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        ButtonCustom(ButtonType.SPECIAL, "Guardar Cambios") {

        }
        ButtonCustom(ButtonType.DANGER, "Eliminar Vaca") {

        }
        ButtonCustom(ButtonType.DANGER, "Notificar Muerte") {

        }
    }
}