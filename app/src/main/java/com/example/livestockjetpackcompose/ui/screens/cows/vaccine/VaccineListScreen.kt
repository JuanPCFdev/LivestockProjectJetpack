package com.example.livestockjetpackcompose.ui.screens.cows.vaccine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.domain.model.Vaccine
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.cows.vaccine.VaccineListViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun VaccineListScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    onNavigateToRegisterVaccine: () -> Unit,
    onVaccineSelected:(String) -> Unit,
    viewModel: VaccineListViewModel = hiltViewModel()
) {

    val vaccines = viewModel.vaccines.collectAsState()
    val keys = viewModel.keys.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userKey) {
        viewModel.loadVaccines(userKey, farmKey, cowKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Vacunas Registradas")
        BodyVaccineList(
            modifier = Modifier.weight(7f),
            vaccines = vaccines.value,
            keys = keys.value,
            vaccineSelected = onVaccineSelected,
            viewModel = viewModel,
            userKey = userKey,
            farmKey = farmKey,
            cowKey = cowKey
        )
        when (uiState) {
            is VaccineListViewModel.UiState.Error -> {
                Text(
                    text = (uiState as VaccineListViewModel.UiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            is VaccineListViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }
        RegisterNewVaccineButton(Modifier.weight(2f)) {
            onNavigateToRegisterVaccine()
        }
    }
}

@Composable
private fun BodyVaccineList(
    modifier: Modifier,
    vaccines: List<Vaccine>?,
    keys: List<String>?,
    vaccineSelected: (String) -> Unit,
    viewModel: VaccineListViewModel,
    userKey: String,
    farmKey: String,
    cowKey: String
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .background(background_app)
    ) {
        if (vaccines != null && keys != null) {

            val mixList = vaccines.zip(keys)

            items(mixList) { (vaccine, key) ->
                val swipeLeft = SwipeAction(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(12.dp)
                        )
                    },
                    background = Color.Red,
                    isUndo = true,
                    onSwipe = {
                        viewModel.deleteSelectedVaccine(userKey, farmKey, cowKey, key)
                    }
                )

                SwipeableActionsBox(endActions = listOf(swipeLeft)) {
                    ListItem(vaccineName = vaccine.vaccineName) {
                        vaccineSelected(key)
                    }
                }

            }
        } else {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Aun no hay vacunas registradas de este tipo",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ListItem(vaccineName: String, onClickedItem: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            onClickedItem()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = vaccineName,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }
    }
}

@Composable
private fun RegisterNewVaccineButton(modifier: Modifier, onButtonPressed: () -> Unit) {
    Box(modifier = modifier.fillMaxSize()) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar nueva vacuna") {
            onButtonPressed()
        }
    }
}