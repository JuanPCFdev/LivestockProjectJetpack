package com.example.livestockjetpackcompose.ui.screens.cows.lifting.weight

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.viewmodels.cows.lifting.weight.RegisterWeightViewModel
import java.util.Calendar

@Composable
fun RegisterWeightScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    onRegisterDone: () -> Unit,
    viewModel: RegisterWeightViewModel = hiltViewModel()
) {

    val date = viewModel.date.collectAsState()
    val weight = viewModel.weight.collectAsState()
    val diet = viewModel.diet.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Registrar pesaje")
        BodyRegisterWeight(
            modifier = Modifier.weight(3f),
            date = date.value,
            weight = weight.value,
            diet = diet.value,
            context = context,
            onDateChange = { newDate -> viewModel.onDateChange(newDate) },
            onWeightChange = { newWeight -> viewModel.onWeightChange(newWeight) },
            onDietChange = { newDiet -> viewModel.onDietChange(newDiet) }
        )

        when (uiState) {
            is RegisterWeightViewModel.UiState.Error -> Text(
                text = (uiState as RegisterWeightViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            RegisterWeightViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }

        SaveChangesButton(Modifier.weight(1f)) {
            viewModel.onRegisterWeight(userKey, farmKey, cowKey, onRegisterDone)
        }
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun BodyRegisterWeight(
    modifier: Modifier,
    date: String,
    weight: String,
    diet: String,
    context: Context,
    onDateChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onDietChange: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 8.dp)
                .background(background_app),
            verticalArrangement = Arrangement.Center
        ) {
            DateTextField(context, date, onDateChange)
            OutlinedTextFieldCustom(
                TextFieldType.NUMBER, "Peso",
                text = weight,
                onValueChange = onWeightChange
            )
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Dieta",
                text = diet,
                onValueChange = onDietChange
            )
        }
    }
}

@Composable
private fun SaveChangesButton(modifier: Modifier, onButtonPressed: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Guardar Cambios") {
            onButtonPressed()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateTextField(
    context: Context,
    dateText: String,
    onDateChange: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = dateText,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clickable { showDatePicker(context, onDateChange) },
            placeholder = { Text("Fecha de muestra") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Gray,
                focusedBorderColor = Color.Gray
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