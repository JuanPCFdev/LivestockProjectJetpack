package com.example.livestockjetpackcompose.ui.screens.cows.breeading.insemination

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading.RegisterInseminationViewModel

@Composable
fun RegisterInseminationScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    onRegisterDone: () -> Unit,
    viewModel: RegisterInseminationViewModel = hiltViewModel()
) {

    val date = viewModel.date.collectAsState()
    val description = viewModel.description.collectAsState()
    val spermOrigin = viewModel.spermOrigin.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Registrar Inseminación")
        CardRegisterInsemination(
            modifier = Modifier.weight(3f),
            date = date.value,
            description = description.value,
            spermOrigin = spermOrigin.value,
            context = context,
            onDateChange = { newDate -> viewModel.onDateChange(newDate) },
            onDescriptionChange = { newDescription -> viewModel.onDescriptionChange(newDescription) },
            onSpermOriginChange = { newSpermOrigin -> viewModel.onSpermOriginChange(newSpermOrigin) }
        )

        when (uiState) {
            is RegisterInseminationViewModel.UiState.Error -> Text(
                text = (uiState as RegisterInseminationViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            RegisterInseminationViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }

        ButtonRegisterInsemination(Modifier.weight(1f)) {
            viewModel.onRegisterInsemination(userKey, farmKey, cowKey, onRegisterDone)
        }
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun CardRegisterInsemination(
    modifier: Modifier,
    date: String,
    description: String,
    spermOrigin: String,
    context: Context,
    onDateChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSpermOriginChange: (String) -> Unit
) {
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DateOutlinedTextFieldCustom(context,date,onDateChange)
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Descripción",
                text = description,
                onValueChange = onDescriptionChange
            )
            OutlinedTextFieldCustom(
                TextFieldType.TEXT, "Procedencia del esperma",
                text = spermOrigin,
                onValueChange = onSpermOriginChange
            )
        }
    }
}

@Composable
private fun ButtonRegisterInsemination(modifier: Modifier, onButtonPressed: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar") {
            onButtonPressed()
        }
    }
}