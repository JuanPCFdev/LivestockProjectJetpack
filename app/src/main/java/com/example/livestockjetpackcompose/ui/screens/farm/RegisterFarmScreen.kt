package com.example.livestockjetpackcompose.ui.screens.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.viewmodels.farm.ListFarmViewModel
import com.example.livestockjetpackcompose.ui.viewmodels.farm.RegisterFarmViewModel
import com.example.livestockjetpackcompose.ui.viewmodels.farm.RegisterFarmViewModel.*

@Composable
fun RegisterFarmScreen(
    modifier: Modifier,
    userKey: String,
    onRegisterFarmDone: () -> Unit,
    viewModel: RegisterFarmViewModel = hiltViewModel(),
    listFarmViewModel: ListFarmViewModel = hiltViewModel()
) {

    val name by viewModel.name.collectAsState("")
    val address by viewModel.address.collectAsState("")
    val hectares by viewModel.hectares.collectAsState("")
    val capacity by viewModel.capacity.collectAsState("")
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Registrar Finca")
        Body(
            modifier = Modifier.weight(3f),
            onNameChange = { newName -> viewModel.onNameChange(newName) },
            onAddressChange = { newAddress -> viewModel.onAddressChange(newAddress) },
            onHectaresChange = { newHectares -> viewModel.onHectaresChange(newHectares) },
            onCapacityChange = { newCapacity -> viewModel.onCapacityChange(newCapacity) },
            name = name,
            address = address,
            hectares = hectares,
            capacity = capacity
        )

        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text(
                text = (uiState as UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> Unit
        }

        Buttons(Modifier.weight(1f)) {
            viewModel.registerFarm(userKey, listFarmViewModel) { onRegisterFarmDone() }
        }
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun Body(
    modifier: Modifier,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onHectaresChange: (String) -> Unit,
    onCapacityChange: (String) -> Unit,
    name: String,
    address: String,
    hectares: String,
    capacity: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextFieldCustom(
            TextFieldType.TEXT, "Nombre de la finca",
            text = name,
            onValueChange = onNameChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.TEXT, "Dirección de la finca",
            text = address,
            onValueChange = onAddressChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.NUMBER, "Hectareas de la finca",
            text = hectares,
            onValueChange = onHectaresChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.NUMBER, "Capacidad de la finca",
            text = capacity,
            onValueChange = onCapacityChange
        )
    }
}

@Composable
private fun Buttons(modifier: Modifier, onRegisterFarm: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registra tu Finca") {
            onRegisterFarm()
        }
    }
}