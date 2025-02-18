package com.example.livestockjetpackcompose.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.ui.theme.border_text_field

enum class TextFieldType {
    TEXT,
    PASSWORD,
    NUMBER,
    DISABLED,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldCustom(
    type: TextFieldType,
    placeHolder: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val customColors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        cursorColor = border_text_field,
        focusedBorderColor = border_text_field,
        disabledTextColor = Color.Black,
        disabledBorderColor = Color.Black,
        disabledPrefixColor = Color.Black,
        disabledLabelColor = Color.Black,
        disabledPlaceholderColor = Color.Black,
        focusedPlaceholderColor = Color.Cyan,
        unfocusedPlaceholderColor = Color.Black
    )

    val commonModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)

    when (type) {
        TextFieldType.TEXT -> {
            OutlinedTextField(
                value = text,
                onValueChange = { onValueChange(it) },
                enabled = true,
                modifier = commonModifier,
                placeholder = { Text(placeHolder) },
                label = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                colors = customColors
            )
        }

        TextFieldType.PASSWORD -> {
            OutlinedTextField(
                value = text,
                onValueChange = { onValueChange(it) },
                enabled = true,
                modifier = commonModifier,
                placeholder = { Text(placeHolder) },
                label = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = customColors,
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_eye),
                            contentDescription = ""
                        )
                    }
                }
            )
        }

        TextFieldType.NUMBER -> {
            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                enabled = true,
                modifier = commonModifier,
                placeholder = { Text(placeHolder) },
                label = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = customColors
            )
        }

        TextFieldType.DISABLED -> {
            OutlinedTextField(
                value = text,
                onValueChange = { },
                enabled = false,
                modifier = commonModifier,
                placeholder = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                colors = customColors
            )
        }
    }
}