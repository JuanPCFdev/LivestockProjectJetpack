package com.example.livestockjetpackcompose.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.ui.theme.border_text_field

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBreedOutlinedTextField(breedText: String, onBreedChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(
        "Brahman",
        "Gyr",
        "Holstein",
        "Jersey",
        "Normando",
        "Romosinuano",
        "Blanco Orejinegro",
        "Velásquez",
        "Criollo Caqueteño",
        "San Martinero",
        "Costeño con Cuernos",
        "Hartón del Valle",
        "Lucerna",
        "Chino Santandereano",
        "Criollo Casanare",
        "Blonda de Aquitania",
        "Charolesa",
        "Fleckvieh",
        "Frisona",
        "Limusina",
        "Parda"
    )

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = breedText,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clickable {
                    expanded = true
                },
            placeholder = { Text("Raza") },
            singleLine = true,
            maxLines = 1,
            minLines = 1,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = border_text_field,
                focusedBorderColor = border_text_field,
                disabledBorderColor = Color.Black,
                disabledPrefixColor = Color.Black,
                disabledLabelColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
                focusedPlaceholderColor = Color.Cyan,
                unfocusedPlaceholderColor = Color.Black,
                disabledTextColor = Color.Black
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .widthIn(min = 150.dp, max = 400.dp)
                .heightIn(min = 100.dp, max = 400.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onBreedChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}