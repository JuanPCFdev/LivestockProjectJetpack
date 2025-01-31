package com.example.livestockjetpackcompose.ui.screens.cows.breeading

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
import com.example.livestockjetpackcompose.domain.model.Insemination
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.viewmodels.cows.breeading.InseminationStatsViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun InseminationStatsScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowKey: String,
    navigateToRegisterInsemination: () -> Unit,
    viewModel: InseminationStatsViewModel = hiltViewModel()
) {

    val inseminationList = viewModel.inseminationList.collectAsState()
    val keyList = viewModel.keys.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedInsemination by remember { mutableStateOf<Insemination?>(null) }

    LaunchedEffect(userKey) {
        viewModel.loadInseminationData(userKey, farmKey, cowKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Estadísticas Vaca 112233")
        CardInseminationList(
            modifier = Modifier.weight(2f),
            inseminationList = inseminationList.value,
            keys = keyList.value,
            inseminationSelected = { inseminationDate ->
                val selected =
                    inseminationList.value?.find { it.inseminationDate == inseminationDate }
                if (selected != null) {
                    selectedInsemination = selected
                    showDialog = true
                }
            },
            viewModel = viewModel,
            userKey = userKey,
            farmKey = farmKey,
            cowKey = cowKey
        )
        StatsBreeadingCard(
            modifier = Modifier.weight(3f),
            textData = "" //Last implementation
        )

        when (uiState) {
            is InseminationStatsViewModel.UiState.Error -> {
                Text(
                    text = (uiState as InseminationStatsViewModel.UiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            InseminationStatsViewModel.UiState.Loading -> CircularProgressIndicator()
            else -> Unit
        }

        RegisterInseminationButton(Modifier.weight(1f)) {
            navigateToRegisterInsemination()
        }

        if (showDialog && selectedInsemination != null) {
            InseminationDetailsDialog(
                insemination = selectedInsemination!!,
                onDismiss = { showDialog = false }
            )
        }
    }
}


@Composable
private fun CardInseminationList(
    modifier: Modifier,
    inseminationList: List<Insemination>?,
    keys: List<String>?,
    inseminationSelected: (String) -> Unit,
    viewModel: InseminationStatsViewModel,
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
        if (inseminationList != null && keys != null) {
            val mixList = inseminationList.zip(keys)

            items(mixList) { (insemination, key) ->
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
                        viewModel.deleteInsemination(userKey, farmKey, cowKey, key)
                    }
                )

                SwipeableActionsBox(endActions = listOf(swipeLeft)) {
                    InseminationItem(inseminationDate = insemination.inseminationDate) {
                        inseminationSelected(insemination.inseminationDate)
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
private fun InseminationItem(inseminationDate: String, onClickedItem: () -> Unit) {
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
                text = inseminationDate,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }
    }
}


@Composable
private fun InseminationDetailsDialog(
    insemination: Insemination,
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
                    text = "Fecha: ${insemination.inseminationDate}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Descripción: ${insemination.descOfInsemination}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Origen del esperma: ${insemination.spermOrigin}",
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
private fun StatsBreeadingCard(modifier: Modifier, textData:String) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(textData, modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
private fun RegisterInseminationButton(modifier: Modifier, onButtonPressed: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Inseminación") {
            onButtonPressed()
        }
    }
}