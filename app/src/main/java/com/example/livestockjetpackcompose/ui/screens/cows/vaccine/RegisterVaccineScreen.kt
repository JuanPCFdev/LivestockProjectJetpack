package com.example.livestockjetpackcompose.ui.screens.cows.vaccine

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.DateOutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine.RegisterVaccineViewModel

@Composable
fun RegisterVaccineScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    onRegisterDone: () -> Unit,
    viewModel: RegisterVaccineViewModel = hiltViewModel()
) {
    val name = viewModel.vaccineName.collectAsState()
    val cost = viewModel.vaccineCost.collectAsState()
    val date = viewModel.vaccineDate.collectAsState()
    val supplier = viewModel.supplier.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Registro de Vacuna")
        BodyRegisterVaccine(
            modifier = Modifier.weight(3f),
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
            is RegisterVaccineViewModel.UiState.Error -> Text(
                text = (uiState as RegisterVaccineViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            RegisterVaccineViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }
        RegisterButton(Modifier.weight(1f)) {
            viewModel.onRegisterVaccine(userKey, farmKey, cowKey, onRegisterDone = {
                onRegisterDone()
            })
        }
    }
}

@Composable
private fun BodyRegisterVaccine(
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
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                DateOutlinedTextFieldCustom(context,date,onDateChange)
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
private fun RegisterButton(modifier: Modifier, onPressButton: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Vacuna") {
            onPressButton()
        }
    }
}