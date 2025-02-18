package com.example.livestockjetpackcompose.ui.screens.cows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.domain.model.Cattle
import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.SimpleMultipurposeCard
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.cows.MultiCowViewModel
import com.example.livestockjetpackcompose.ui.viewmodels.farm.ListFarmViewModel.UiState
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun MultiCowScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowFilter: CowTypeFilter,
    navigateToRegisterLiftingCow: () -> Unit,
    navigateToRegisterBreedingCow: () -> Unit,
    navigateToCowsResume: (String, CowTypeFilter) -> Unit,
    viewModel: MultiCowViewModel = hiltViewModel()
) {

    val cows = viewModel.cows.collectAsState()
    val cowsKeys = viewModel.cowsKeys.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userKey) {
        viewModel.loadUserFarmCows(userKey, farmKey, cowFilter)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(PaddingValues(start = 10.dp, end = 10.dp, bottom = 45.dp, top = 5.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Title(
            modifier = Modifier.weight(1f),
            title = when (cowFilter) {
                CowTypeFilter.LIFTING -> "Ganado de Levante"
                CowTypeFilter.BREEADING -> "Ganado de Cria"
                CowTypeFilter.CORRAL -> "Ganado de Corral"
                CowTypeFilter.DEAD -> "Ganado Muerto"
                CowTypeFilter.SOLD -> "Ganado Vendido"
            }
        )

        ItemsList(
            modifier = Modifier.weight(5f),
            cows = cows.value,
            cowsKeys = cowsKeys.value,
            cowSelected = { cowKey, cowType ->
                navigateToCowsResume(cowKey, cowFilter)
            },
            userKey = userKey,
            farmKey = farmKey,
            cowType = cowFilter,
            viewModel = viewModel
        )

        when (cowFilter) {
            CowTypeFilter.LIFTING -> {
                ButtonAction(Modifier.weight(1f), "Registrar Ganado de Levante") {
                    navigateToRegisterLiftingCow()
                }
            }

            CowTypeFilter.BREEADING -> {
                ButtonAction(Modifier.weight(1f), "Registrar Ganado de Cria") {
                    navigateToRegisterBreedingCow()
                }
            }

            else -> Unit
        }

        when (uiState) {
            is MultiCowViewModel.UiState.Loading -> CircularProgressIndicator()
            is MultiCowViewModel.UiState.Error -> Text(
                text = (uiState as UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> Unit
        }

    }
}

@Composable
private fun ItemsList(
    modifier: Modifier,
    cows: List<Cattle>?,
    cowsKeys: List<String>?,
    cowSelected: (String, CowTypeFilter) -> Unit,
    userKey: String,
    farmKey: String,
    cowType: CowTypeFilter,
    viewModel: MultiCowViewModel
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .background(background_app)
    ) {
        if (cows != null && cowsKeys != null && cows.isNotEmpty()) {
            val cowsWhitKeys = cows.zip(cowsKeys)

            items(cowsWhitKeys) { (cow, cowKey) ->

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
                        viewModel.deleteSelectedCow(
                            userKey = userKey,
                            farmKey = farmKey,
                            cowKey = cowKey,
                            cowFilter = cowType
                        )
                    }
                )

                SwipeableActionsBox(endActions = listOf(swipeLeft)) {
                    SimpleMultipurposeCard(text = cow.marking) {
                        cowSelected(cowKey, cowType)
                    }
                }

            }

        } else {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Aun no hay vacas registradas de este tipo",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ButtonAction(modifier: Modifier, text: String, onButtonClicked: () -> Unit) {
    Box(modifier.fillMaxWidth()) {
        ButtonCustom(ButtonType.SPECIAL, text) {
            onButtonClicked()
        }
    }
}
