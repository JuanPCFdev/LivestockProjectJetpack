package com.example.livestockjetpackcompose.ui.screens.cows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.domain.utils.CowTypeFilter
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.cows.MultiCowViewModel
import com.example.livestockjetpackcompose.ui.viewmodels.farm.ListFarmViewModel.UiState


@Composable
fun MultiCowScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    cowFilter: CowTypeFilter,
    navigateToRegisterLiftingCow: () -> Unit,
    navigateToRegisterBreedingCow: () -> Unit,
    viewModel: MultiCowViewModel = hiltViewModel()
) {

    val cows = viewModel.cows.collectAsState()
    val cowsKeys = viewModel.cowsKeys.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userKey) {
        viewModel.loadUserFarmCows(userKey, farmKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(PaddingValues(start = 10.dp, end = 10.dp, bottom = 45.dp, top = 5.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (cowFilter) {
            CowTypeFilter.LIFTING -> {
                Title(modifier = Modifier.weight(1f), "Ganado de Levante")
                ItemsList(Modifier.weight(5f))
                ButtonAction(Modifier.weight(1f), "Registrar Ganado de Levante") {
                    navigateToRegisterLiftingCow()
                }
            }

            CowTypeFilter.BREEADING -> {
                Title(modifier = Modifier.weight(1f), "Ganado de Cria")
                ItemsList(Modifier.weight(5f))
                ButtonAction(Modifier.weight(1f), "Registrar Ganado de Cria") {
                    navigateToRegisterBreedingCow()
                }
            }

            CowTypeFilter.CORRAL -> {
                Title(modifier = Modifier.weight(1f), "Ganado de Corral")
                ItemsList(Modifier.weight(5f))
            }

            CowTypeFilter.DEAD -> {
                Title(modifier = Modifier.weight(1f), "Ganado Muerto")
                ItemsList(Modifier.weight(5f))
            }

            CowTypeFilter.SOLD -> {
                Title(modifier = Modifier.weight(1f), "Ganado Vendido")
                ItemsList(Modifier.weight(5f))
            }
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
private fun ItemsList(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.padding(5.dp),
        content = {
            item {
                Text("Item 1")
            }
            item {
                Text("Item 2")
            }
            item {
                Text("Item 3")
            }
            item {
                Text("Item 4")
            }
            item {
                Text("Item 5")
            }
        }
    )
}

@Composable
private fun ButtonAction(modifier: Modifier, text: String, onButtonClicked: () -> Unit) {
    Box(modifier.fillMaxWidth()) {
        ButtonCustom(ButtonType.SPECIAL, text) {
            onButtonClicked()
        }
    }
}


