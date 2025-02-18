package com.example.livestockjetpackcompose.ui.screens.cows.lifting.weight

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
import com.example.livestockjetpackcompose.ui.viewmodels.cows.lifting.weight.RegisterWeightViewModel

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
            DateOutlinedTextFieldCustom(context, date, onDateChange)
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