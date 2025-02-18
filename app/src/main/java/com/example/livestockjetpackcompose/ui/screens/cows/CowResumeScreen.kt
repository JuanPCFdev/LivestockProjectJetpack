package com.example.livestockjetpackcompose.ui.screens.cows

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.theme.border_text_field
import com.example.livestockjetpackcompose.ui.utils.CustomBreedOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.DateOutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.viewmodels.cows.CowResumeViewModel

@Composable
fun CowResumeScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    cowTypeFilter: CowTypeFilter,
    navigateToVaccineListHome: () -> Unit,
    navigateToLiftingStats: () -> Unit,
    navigateToInsemination: () -> Unit,
    navigateToBreadingStats: () -> Unit,
    viewModel: CowResumeViewModel = hiltViewModel()
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

    LaunchedEffect(userKey) {
        viewModel.getCowData(userKey, farmKey, cowKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Información de la vaca")

        when (uiState) {
            is CowResumeViewModel.UiState.Loading -> CircularProgressIndicator()
            is CowResumeViewModel.UiState.Error -> Text(
                text = (uiState as CowResumeViewModel.UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> {
                ListTextFieldEditCow(
                    modifier = Modifier.weight(4f),
                    cowTypeFilter = cowTypeFilter,
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

                ButtonsListCow(
                    modifier = Modifier.weight(3f),
                    onEditPressed = {
                        val cowTypeMap = mapOf(
                            CowTypeFilter.LIFTING to "lifting",
                            CowTypeFilter.BREEADING to "breeading",
                            CowTypeFilter.CORRAL to "corral"
                        )

                        cowTypeMap[cowTypeFilter]?.let { cowType ->
                            viewModel.editCow(userKey, farmKey, cowKey, cowType)
                        }
                    },
                    onVaccinePressed = { navigateToVaccineListHome() },
                    onWeightPressed = { navigateToLiftingStats() },
                    onInseminationPressed = { navigateToInsemination() },
                    onBreadingPressed = { navigateToBreadingStats() },
                    cowTypeFilter = cowTypeFilter
                )

            }
        }

    }
}

@Composable
private fun ButtonsListCow(
    modifier: Modifier,
    onEditPressed: () -> Unit,
    onVaccinePressed: () -> Unit,
    onWeightPressed: () -> Unit,
    onInseminationPressed: () -> Unit,
    onBreadingPressed: () -> Unit,
    cowTypeFilter: CowTypeFilter
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .background(background_app),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (cowTypeFilter) {
            CowTypeFilter.LIFTING -> {
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Editar") {
                        onEditPressed()
                    }
                }
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Consultar Vacunas") {
                        onVaccinePressed()
                    }
                }
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Registrar Peso") {
                        onWeightPressed()
                    }
                }
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Reportar Muerte") {

                    }
                }
            }

            CowTypeFilter.BREEADING -> {
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Editar") {
                        onEditPressed()
                    }
                }
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Consultar Vacunas") {
                        onVaccinePressed()
                    }
                }
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Registrar Inseminación") {
                        onInseminationPressed()
                    }
                }
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Registrar Parto") {
                        onBreadingPressed()
                    }
                }
                item {
                    ButtonCustom(ButtonType.SPECIAL, "Reportar Muerte") {

                    }
                }
            }

            else -> Unit
        }
    }
}

@Composable
private fun ListTextFieldEditCow(
    modifier: Modifier,
    cowTypeFilter: CowTypeFilter,
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .background(background_app),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
            StateOutlinedTextField(
                stateText = state,
                onStateChange = onStateChange
            )
        }
        item {
            SexOutlinedTextField(
                sexText = sex,
                onSexChange = onSexChange
            )
        }

        item {

            when (cowTypeFilter) {

                CowTypeFilter.LIFTING -> {
                    OutlinedTextFieldCustom(
                        TextFieldType.NUMBER, "Costo (COP)",
                        text = cost,
                        onValueChange = onCostChange
                    )
                    CheckBoxCastratedCow(
                        castrated = castrated,
                        onCastratedChange = onCastratedChange
                    )
                }

                CowTypeFilter.BREEADING -> {
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
                }

                else -> {
                    Unit
                }
            }

        }
    }
}

@Composable
private fun CheckBoxCastratedCow(castrated: Boolean, onCastratedChange: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("¿La vaca ha sido castrada?")
            Checkbox(
                checked = castrated,
                onCheckedChange = onCastratedChange,
                enabled = true
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StateOutlinedTextField(stateText: String, onStateChange: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Sana", "Enferma", "Preñada")

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = stateText,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clickable {
                    expanded = true
                },
            placeholder = { Text("Estado") },
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

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.widthIn(min = 150.dp, max = 250.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onStateChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SexOutlinedTextField(sexText: String, onSexChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Macho", "Hembra")

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = sexText,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .clickable {
                    expanded = true
                },
            placeholder = { Text("Sexo") },
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

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.widthIn(min = 150.dp, max = 250.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSexChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}