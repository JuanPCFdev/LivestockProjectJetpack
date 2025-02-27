package com.example.livestockjetpackcompose.ui.screens.cows

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
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.theme.border_text_field
import com.example.livestockjetpackcompose.ui.utils.CustomBreedOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.CustomCheckBoxCastratedCow
import com.example.livestockjetpackcompose.ui.utils.CustomSexOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.CustomStateOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.DateOutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.viewmodels.cows.RegisterCowViewModel

@Composable
fun RegisterCowScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowType: Boolean,
    onRegisterCowDone: () -> Unit,
    viewModel: RegisterCowViewModel = hiltViewModel()
) {

    val marking = viewModel.marking.collectAsState()
    val birthdate = viewModel.birthdate.collectAsState()
    val weight = viewModel.weight.collectAsState()
    val age = viewModel.age.collectAsState()
    val breed = viewModel.breed.collectAsState()
    val state = viewModel.state.collectAsState()
    val sex = viewModel.sex.collectAsState()
    val markingFather = viewModel.markingFather.collectAsState()
    val markingMother = viewModel.markingMother.collectAsState()
    val castrated = viewModel.castrated.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val cost = viewModel.cost.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (cowType) {
            Title(Modifier.weight(1f), "Registar Ganado de Cría")
        } else {
            Title(Modifier.weight(1f), "Registar Ganado de Levante")
        }

        when (uiState) {
            is RegisterCowViewModel.UiState.Loading -> {
                CircularProgressIndicator()
            }

            is RegisterCowViewModel.UiState.Error -> Text(
                text = (uiState as RegisterCowViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> {
                Unit
            }
        }

        BodyRegisterCow(
            modifier = Modifier.weight(4f),
            cowType = cowType,
            marking = marking.value,
            birthdate = birthdate.value,
            weight = weight.value,
            age = age.value,
            breed = breed.value,
            state = state.value,
            sex = sex.value,
            markingFather = markingFather.value,
            markingMother = markingMother.value,
            castrated = castrated.value,
            cost = cost.value,
            onMarkingChange = { newMarking -> viewModel.onMarkingChange(newMarking) },
            onBirthdateChange = { newBirthdate -> viewModel.onBirthdateChange(newBirthdate) },
            onWeightChange = { newWeight -> viewModel.onWeightChange(newWeight) },
            onAgeChange = { newAge -> viewModel.onAgeChange(newAge) },
            onBreedChange = { newBreed -> viewModel.onBreedChange(newBreed) },
            onStateChange = { newState -> viewModel.onStateChange(newState) },
            onSexChange = { newSex -> viewModel.onSexChange(newSex) },
            onMarkingFatherChange = { newMarkingFather ->
                viewModel.onMarkingFatherChange(
                    newMarkingFather
                )
            },
            onMarkingMotherChange = { newMarkingMother ->
                viewModel.onMarkingMotherChange(
                    newMarkingMother
                )
            },
            onCastratedChange = { newCastrated -> viewModel.onCastratedChange(newCastrated) },
            onCostChange = { newCost -> viewModel.onCostChange(newCost) },
            context = context
        )
        ConfirmButton(Modifier.weight(1f)) {
            viewModel.registerNewCow(
                userKey = userKey,
                farmKey = farmKey,
                onRegisterCowDone = {
                    onRegisterCowDone()
                },
                type = cowType
            )
        }
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun BodyRegisterCow(
    modifier: Modifier,
    cowType: Boolean,
    marking: String,
    birthdate: String,
    weight: String,
    age: String,
    breed: String,
    state: String,
    sex: String,
    markingFather: String,
    markingMother: String,
    castrated: Boolean,
    cost: String,
    onMarkingChange: (String) -> Unit,
    onBirthdateChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onBreedChange: (String) -> Unit,
    onStateChange: (String) -> Unit,
    onSexChange: (String) -> Unit,
    onMarkingFatherChange: (String) -> Unit,
    onMarkingMotherChange: (String) -> Unit,
    onCastratedChange: (Boolean) -> Unit,
    onCostChange: (String) -> Unit,
    context: Context
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
                .background(background_app)
        ) {
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Marcación",
                    text = marking,
                    onValueChange = onMarkingChange
                )
            }
            item {
                DateOutlinedTextFieldCustom(
                    context = context,
                    birthdateText = birthdate,
                    onBirthdateChange = onBirthdateChange
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.NUMBER, "Peso (en Kg)",
                    text = weight,
                    onValueChange = onWeightChange
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.DISABLED, "Edad (en meses)",
                    text = age,
                    onValueChange = onAgeChange
                )
            }
            item {
                CustomBreedOutlinedTextField(breed, onBreedChange)
            }
            item {
                CustomStateOutlinedTextField(state, onStateChange)
            }
            item {
                CustomSexOutlinedTextField(sex, onSexChange)
            }

            item {
                if (cowType) {
                    OutlinedTextFieldCustom(
                        TextFieldType.TEXT, "Marcación Madre",
                        text = markingMother,
                        onValueChange = onMarkingMotherChange
                    )
                    OutlinedTextFieldCustom(
                        TextFieldType.TEXT, "Marcación Padre",
                        text = markingFather,
                        onValueChange = onMarkingFatherChange
                    )
                } else {
                    OutlinedTextFieldCustom(
                        TextFieldType.NUMBER, "Costo (COP)",
                        text = cost,
                        onValueChange = onCostChange
                    )
                    CustomCheckBoxCastratedCow(castrated, onCastratedChange)
                }
            }
        }
    }
}

@Composable
private fun ConfirmButton(modifier: Modifier, onRegisterCow: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Vaca") {
            onRegisterCow()
        }
    }
}
