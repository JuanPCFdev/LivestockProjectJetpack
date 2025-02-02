package com.example.livestockjetpackcompose.ui.screens.cows.breeading.born

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.domain.model.BreedingPerformance
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading.BreadingPerformanceStatsViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun BreadingPerformanceStats(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    navigateToRegisterBreadingPerformance: () -> Unit,
    viewModel: BreadingPerformanceStatsViewModel = hiltViewModel()
) {

    val pBreadingList = viewModel.pBeadingList.collectAsState()
    val keys = viewModel.keys.collectAsState()
    val uiState by viewModel.uiState.collectAsState()


    var showDialog by remember { mutableStateOf(false) }
    var selectedBreadingPerformance by remember { mutableStateOf<BreedingPerformance?>(null) }

    LaunchedEffect(userKey) {
        viewModel.loadBreadingPerformanceData(userKey, farmKey, cowKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Registros de Partos")
        CardInseminationList(
            modifier = Modifier.weight(2f),
            breadingPerformanceList = pBreadingList.value,
            keys = keys.value,
            breadingPerformanceSelected = { date ->
                val selected =
                    pBreadingList.value?.find { it.PBDate == date }
                if (selected != null) {
                    selectedBreadingPerformance = selected
                    showDialog = true
                }
            },
            viewModel = viewModel,
            userKey = userKey,
            farmKey = farmKey,
            cowKey = cowKey
        )

        when (uiState) {
            is BreadingPerformanceStatsViewModel.UiState.Error -> TODO()
            BreadingPerformanceStatsViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }

        RegisterBreadingPerformanceButton(Modifier.weight(1f)) {
            navigateToRegisterBreadingPerformance()
        }

        if (showDialog && selectedBreadingPerformance != null) {
            BreeadingPerformanceDetailsDialog(
                breadingP = selectedBreadingPerformance!!,
                onDismiss = { showDialog = false }
            )
        }

    }
}

@Composable
private fun CardInseminationList(
    modifier: Modifier,
    breadingPerformanceList: List<BreedingPerformance>?,
    keys: List<String>?,
    breadingPerformanceSelected: (String) -> Unit,
    viewModel: BreadingPerformanceStatsViewModel,
    userKey: String,
    farmKey: String,
    cowKey: String
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 11.dp)
            .background(background_app)
    ) {
        if (breadingPerformanceList != null && keys != null) {
            val mixList = breadingPerformanceList.zip(keys)

            items(mixList) { (breadingPerformance, key) ->

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
                        viewModel.deleteBreadingPerformanceData(userKey, farmKey, cowKey, key)
                    }
                )

                SwipeableActionsBox(endActions = listOf(swipeLeft)) {
                    BreadingPerformanceItem(breadingPerformanceDate = breadingPerformance.PBDate) {
                        breadingPerformanceSelected(breadingPerformance.PBDate)
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
private fun BreadingPerformanceItem(breadingPerformanceDate: String, onClickedItem: () -> Unit) {
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
                text = breadingPerformanceDate,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }
    }
}


@Composable
private fun BreeadingPerformanceDetailsDialog(
    breadingP: BreedingPerformance,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Detalles del Registro", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(
                    text = "Fecha: ${breadingP.PBDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Peso Inicial: ${breadingP.PBInitialWeight}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Cria enferma? : ${if (breadingP.PBSick) "Si" else "No"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Cria muerta? : ${if (breadingP.PBDeath) "Si" else "No"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Dieta: ${breadingP.PBDiet}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
private fun RegisterBreadingPerformanceButton(modifier: Modifier, onButtonPressed: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Inseminación") {
            onButtonPressed()
        }
    }
}
