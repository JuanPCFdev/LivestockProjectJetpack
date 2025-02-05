package com.example.livestockjetpackcompose.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.ui.theme.background_danger_button
import com.example.livestockjetpackcompose.ui.theme.background_simple_button
import com.example.livestockjetpackcompose.ui.theme.background_special_button
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
    when (type) {
        TextFieldType.TEXT -> {
            OutlinedTextField(
                value = text,
                onValueChange = { onValueChange(it) },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(placeHolder) },
                label = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
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
            )
        }

        TextFieldType.PASSWORD -> {
            OutlinedTextField(
                value = text,
                onValueChange = { onValueChange(it) },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(placeHolder) },
                label = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
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
                ),
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
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        onValueChange(it)
                    }
                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(placeHolder) },
                label = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = TextFieldDefaults.outlinedTextFieldColors(
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
            )
        }

        TextFieldType.DISABLED -> {
            OutlinedTextField(
                value = text,
                onValueChange = { },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                placeholder = { Text(placeHolder) },
                singleLine = true,
                maxLines = 1,
                minLines = 1,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
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
            )
        }
    }
}

enum class ButtonType {
    SPECIAL,
    SIMPLE,
    DANGER
}

@Composable
fun ButtonCustom(type: ButtonType, buttonText: String, onClicked: () -> Unit) {

    when (type) {

        ButtonType.SPECIAL -> {
            Button(
                onClick = { onClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                colors = ButtonColors(
                    containerColor = background_special_button,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Text(buttonText, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

        ButtonType.SIMPLE -> {
            Button(
                onClick = { onClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                colors = ButtonColors(
                    containerColor = background_simple_button,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Text(
                    buttonText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }

        ButtonType.DANGER -> {
            Button(
                onClick = { onClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                colors = ButtonColors(
                    containerColor = background_danger_button,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Text(buttonText, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

    }

}

@Composable
fun Footer(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.cow),
        modifier = modifier.size(100.dp),
        contentDescription = ""
    )
}

@Composable
fun Title(modifier: Modifier, title: String) {
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CardItem(title: String, icon: Int, onCardSelected: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = {
            onCardSelected()
        },
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                title,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
            Image(
                painter = painterResource(icon),
                modifier = Modifier
                    .size(100.dp)
                    .weight(1f),
                contentDescription = "",
                alignment = Alignment.CenterEnd
            )
        }
    }
}

@Preview
@Composable
fun CowItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Example text",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
            Text(
                "#0831MMA4",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )
        }
    }
}