package com.example.livestockjetpackcompose.ui.utils

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.ui.theme.border_text_field
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOutlinedTextFieldCustom(
    context: Context,
    birthdateText: String,
    onBirthdateChange: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = birthdateText,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clickable { showDatePicker(context, onBirthdateChange) },
            placeholder = { Text("Fecha de Nacimiento") },
            singleLine = true,
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

private fun showDatePicker(
    context: Context,
    onBirthdateChange: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val minDate = Calendar.getInstance().apply {
        add(Calendar.YEAR, -2)
    }.timeInMillis

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            onBirthdateChange(formattedDate)
        },
        currentYear,
        currentMonth,
        currentDay
    ).apply {
        datePicker.maxDate = calendar.timeInMillis
        datePicker.minDate = minDate
    }.show()
}