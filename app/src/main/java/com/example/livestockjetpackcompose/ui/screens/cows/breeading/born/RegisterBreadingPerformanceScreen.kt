package com.example.livestockjetpackcompose.ui.screens.cows.breeading.born

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.CheckBoxSickCow
import com.example.livestockjetpackcompose.ui.utils.CustomBreedOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.CustomSexOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.CustomStateOutlinedTextField
import com.example.livestockjetpackcompose.ui.utils.DateOutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.OutlinedTextFieldCustom
import com.example.livestockjetpackcompose.ui.utils.TextFieldType
import com.example.livestockjetpackcompose.ui.utils.Title
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
                CustomStateOutlinedTextField(stateText = state, onStateChange = onStateChange)
            }
            item {
                CustomSexOutlinedTextField(sex, onSexChange)
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
