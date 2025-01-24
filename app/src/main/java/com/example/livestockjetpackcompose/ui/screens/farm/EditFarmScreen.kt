package com.example.livestockjetpackcompose.ui.screens.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.viewmodels.farm.EditFarmViewModel

@Composable
fun EditFarmScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    onEditFarmDone: () -> Unit,
    viewModel: EditFarmViewModel = hiltViewModel()
) {

    val farmName = viewModel.farmName.collectAsState("")
    val farmAddress = viewModel.farmAddress.collectAsState("")
    val farmHectares = viewModel.farmHectares.collectAsState("")
    val farmCapacity = viewModel.farmCapacity.collectAsState("")
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userKey) {
        viewModel.getFarmData(userKey, farmKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Bienvenido a su finca")

        when (uiState) {
            is EditFarmViewModel.UiState.Loading -> CircularProgressIndicator()
            is EditFarmViewModel.UiState.Error -> Text(
                text = (uiState as EditFarmViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> Unit
        }

        Body(
            Modifier.weight(4f),
            onNameChange = { newName -> viewModel.onFarmNameChange(newName) },
            onAddressChange = { newAddress -> viewModel.onFarmAddressChange(newAddress) },
            onHectaresChange = { newHectares -> viewModel.onFarmHectaresChange(newHectares) },
            onCapacityChange = { newCapacity -> viewModel.onFarmCapacityChange(newCapacity) },
            farmName = farmName.value,
            farmAddress = farmAddress.value,
            farmHectares = farmHectares.value,
            farmCapacity = farmCapacity.value
        )
        Buttons(Modifier.weight(2f)) {
            viewModel.editFarm(userKey, farmKey, onEditFarmDone = { onEditFarmDone() })
        }
    }
}

@Composable
private fun Body(
    modifier: Modifier,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onHectaresChange: (String) -> Unit,
    onCapacityChange: (String) -> Unit,
    farmName: String,
    farmAddress: String,
    farmHectares: String,
    farmCapacity: String
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextFieldCustom(
            TextFieldType.TEXT, "Nombre de la finca",
            text = farmName,
            onValueChange = onNameChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.TEXT, "Dirección de la finca",
            text = farmAddress,
            onValueChange = onAddressChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.NUMBER, "Hectareas de la finca",
            text = farmHectares,
            onValueChange = onHectaresChange
        )
        OutlinedTextFieldCustom(
            TextFieldType.NUMBER, "Capacidad de la finca",
            text = farmCapacity,
            onValueChange = onCapacityChange
        )
    }
}

@Composable
private fun Buttons(modifier: Modifier, onEditFarm: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth()) {
        ButtonCustom(ButtonType.SIMPLE, "Completar edición") {
            onEditFarm()
        }
    }
}