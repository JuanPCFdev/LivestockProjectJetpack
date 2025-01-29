package com.example.livestockjetpackcompose.ui.screens.cows.vaccine

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine.EditVaccineViewModel
import com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine.RegisterVaccineViewModel
import java.util.Calendar

@Composable
fun EditVaccineScreen(
    modifier: Modifier, userKey: String,
    farmKey: String,
    cowKey: String,
    vaccineKey: String,
    onEditDone: () -> Unit,
    viewModel: EditVaccineViewModel = hiltViewModel()
) {
    val name = viewModel.vaccineName.collectAsState()
    val date = viewModel.vaccineDate.collectAsState()
    val cost = viewModel.vaccineCost.collectAsState()
    val supplier = viewModel.supplier.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(userKey) {
        viewModel.loadVaccineData(userKey, farmKey, cowKey, vaccineKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Editar o Eliminar Vacuna")
        CardBody(
            modifier = Modifier.weight(4f),
            name = name.value,
            cost = cost.value,
            date = date.value,
            supplier = supplier.value,
            context = context,
            onNameChange = { newName -> viewModel.onVaccineNameChange(newName) },
            onCostChange = { newCost -> viewModel.onVaccineCostChange(newCost) },
            onDateChange = { newDate -> viewModel.onVaccineDateChange(newDate) },
            onSupplierChange = { newSupplier -> viewModel.onSupplierChange(newSupplier) }
        )
        when (uiState) {
            is EditVaccineViewModel.UiState.Error -> Text(
                text = (uiState as EditVaccineViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
            EditVaccineViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }
        ButtonsVaccine(Modifier.weight(2f)) {
            viewModel.onEditVaccine(userKey, farmKey, cowKey, vaccineKey, onEditDone)
        }
    }
}

@Composable
private fun CardBody(
    modifier: Modifier,
    name: String,
    cost: String,
    date: String,
    supplier: String,
    context: Context,
    onNameChange: (String) -> Unit,
    onCostChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onSupplierChange: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 8.dp)
                .background(background_app),
            verticalArrangement = Arrangement.Center
        ) {
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Nombre de Vacuna",
                    text = name,
                    onValueChange = onNameChange
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Valor de Vacuna",
                    text = cost,
                    onValueChange = onCostChange
                )
            }
            item {
                DateTextField(
                    context = context,
                    dateText = date,
                    onDateChange = onDateChange
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Suministrador",
                    text = supplier,
                    onValueChange = onSupplierChange
                )
            }
        }
    }
}

@Composable
private fun ButtonsVaccine(modifier: Modifier, onButonPressed: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ButtonCustom(ButtonType.SPECIAL, "Guardar Cambios") {
            onButonPressed()
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
            placeholder = { Text("Fecha de Administración") },
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