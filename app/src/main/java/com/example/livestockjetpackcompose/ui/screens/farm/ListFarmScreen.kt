package com.example.livestockjetpackcompose.ui.screens.farm

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.domain.model.Farm
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.viewmodels.farm.ListFarmViewModel
import com.example.livestockjetpackcompose.ui.viewmodels.farm.ListFarmViewModel.UiState
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun ListFarmScreen(
    modifier: Modifier,
    userKey: String,
    navigateToRegisterFarm: (String) -> Unit,
    viewModel: ListFarmViewModel = hiltViewModel(),
    navigateToHomePage: (String) -> Unit
) {
    val farms = viewModel.farms.collectAsState()
    val farmKeys = viewModel.farmsKeys.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userKey) {
        viewModel.loadUserFarms(userKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Listado De Fincas")
        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text(
                text = (uiState as UiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            else -> Unit
        }
        BodyItemsList(
            modifier = Modifier.weight(6f),
            farms = farms.value,
            farmKeys = farmKeys.value,
            farmSelected = navigateToHomePage,
            userKey = userKey,
            viewModel = viewModel
        )
        RegisterFarmButton(Modifier.weight(1f)) {
            navigateToRegisterFarm(userKey)
        }
    }
}

@Composable
private fun BodyItemsList(
    modifier: Modifier,
    farms: List<Farm>?,
    farmKeys: List<String>?,
    farmSelected: (String) -> Unit,
    userKey: String,
    viewModel: ListFarmViewModel
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = background_app),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 8.dp)
                .background(background_app)
        ) {
            if (farms != null && farmKeys != null) {
                val farmsWithKeys = farms.zip(farmKeys)

                items(farmsWithKeys) { (farm, farmKey) ->

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
                            viewModel.deleteFarm(userKey = userKey, farmKey = farmKey)
                        }
                    )

                    SwipeableActionsBox(endActions = listOf(swipeLeft)) {
                        ListItem(farmName = farm.nameFarm) {
                            farmSelected(farmKey)
                        }
                    }
                }
            } else {
                item {
                    Text(text = "Aun no hay fincas registradas")
                }
            }
        }
    }
}

@Composable
private fun ListItem(farmName: String, onClickedItem: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            onClickedItem()
        },
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = farmName,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun RegisterFarmButton(modifier: Modifier, navigateToRegisterFarm: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Finca") {
            navigateToRegisterFarm()
        }
    }
}
