package com.example.livestockjetpackcompose.ui.screens.cows.breeading.born

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.theme.border_text_field
import com.example.livestockjetpackcompose.ui.utils.CheckBoxSickCow
import com.example.livestockjetpackcompose.ui.utils.CustomBreedOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.DateOutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading.RegisterBreadingPerformanceViewModel

@Composable
fun RegisterBornCowScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    onRegisterDone: () -> Unit,
    viewModel: RegisterBreadingPerformanceViewModel = hiltViewModel()
) {

    val marking = viewModel.marking.collectAsState()
    val birthdate = viewModel.birthdate.collectAsState()
    val weight = viewModel.weight.collectAsState()
    val age = viewModel.age.collectAsState()
    val breed = viewModel.breed.collectAsState()
    val state = viewModel.state.collectAsState()
    val sex = viewModel.sex.collectAsState()
    val sick = viewModel.sick.collectAsState()
    val dead = viewModel.dead.collectAsState()
    val diet = viewModel.diet.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Registrar Parto")
        BodyRegisterBornCow(
            modifier = Modifier.weight(4f),
            marking = marking.value,
            birthdate = birthdate.value,
            weight = weight.value,
            age = age.value,
            breed = breed.value,
            state = state.value,
            sex = sex.value,
            sick = sick.value,
            dead = dead.value,
            diet = diet.value,
            onDietChange = { newDiet -> viewModel.onDietChange(newDiet) },
            onMarkingChange = { newMarking -> viewModel.onMarkingChange(newMarking) },
            onBirthdateChange = { newBirthdate -> viewModel.onBirthdateChange(newBirthdate) },
            onWeightChange = { newWeight -> viewModel.onWeightChange(newWeight) },
            onAgeChange = { newAge -> viewModel.onAgeChange(newAge) },
            onBreedChange = { newBreed -> viewModel.onBreedChange(newBreed) },
            onStateChange = { newState -> viewModel.onStateChange(newState) },
            onSexChange = { newSex -> viewModel.onSexChange(newSex) },
            onSickChange = { newSick -> viewModel.onSickChange(newSick) },
            onDeadChange = { newDead -> viewModel.onDeadChange(newDead) },
            context = context
        )
        ConfirmButton(Modifier.weight(1f)) {
            viewModel.registerNewCow(userKey, farmKey, cowKey, onRegisterDone)
        }
    }
}
//No se registra correctamente la vaca, solo el breading performance
@Composable
private fun BodyRegisterBornCow(
    modifier: Modifier,
    marking: String,
    birthdate: String,
    weight: String,
    age: String,
    breed: String,
    state: String,
    sex: String,
    sick: Boolean,
    dead: Boolean,
    diet: String,
    onDietChange: (String) -> Unit,
    onMarkingChange: (String) -> Unit,
    onBirthdateChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onBreedChange: (String) -> Unit,
    onStateChange: (String) -> Unit,
    onSexChange: (String) -> Unit,
    onSickChange: (Boolean) -> Unit,
    onDeadChange: (Boolean) -> Unit,
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
                    TextFieldType.NUMBER, "Peso",
                    text = weight,
                    onValueChange = onWeightChange
                )
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.DISABLED, "Edad",
                    text = age,
                    onValueChange = onAgeChange
                )
            }
            item {
                CustomBreedOutlinedTextField(breed, onBreedChange)
            }
            item {
                StateOutlinedTextField(stateText = state, onStateChange = onStateChange)
            }
            item {
                SexOutlinedTextField(sexText = sex, onSexChange = onSexChange)
            }

            item {
                CheckBoxSickCow(sick = sick, onSickChange = onSickChange)
            }

            item {
                CheckBoxDeadCow(dead = dead, onDeadChange = onDeadChange)
            }
            item {
                OutlinedTextFieldCustom(
                    TextFieldType.TEXT, "Dieta",
                    text = diet,
                    onValueChange = onDietChange
                )
            }
        }
    }
}

@Composable
private fun CheckBoxDeadCow(dead: Boolean, onDeadChange: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("¿La vaca nacio muerta?")
            Checkbox(
                checked = dead,
                onCheckedChange = onDeadChange,
                enabled = true
            )
        }
    }
}

@Composable
private fun ConfirmButton(modifier: Modifier, onRegisterCow: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Nacimiento") {
            onRegisterCow()
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
