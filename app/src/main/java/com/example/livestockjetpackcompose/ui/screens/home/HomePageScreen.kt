package com.example.livestockjetpackcompose.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.ui.utils.CardItem
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.viewmodels.home.HomePageViewModel

@Composable
fun HomePageScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    viewModel: HomePageViewModel = hiltViewModel(),
    navigateToFarm: () -> Unit,
    navigateToUser: () -> Unit,
    navigateToCattle: () -> Unit,
    navigateToFinance: () -> Unit
) {

    val farm = viewModel.farm.collectAsState()
    viewModel.getFarmData(userKey, farmKey)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(PaddingValues(start = 10.dp, end = 10.dp, bottom = 45.dp, top = 5.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(
            Modifier.weight(1f),
            if (farm.value != null) {
                farm.value?.nameFarm ?: "No Data"
            } else {
                "No Data"
            }
        )
        ElementsList(
            Modifier.weight(5f),
            navigateToFarm = { navigateToFarm() },
            navigateToUser = { navigateToUser() },
            navigateToCattle = { navigateToCattle() },
            navigateToFinance = { navigateToFinance() }
        )
        Footer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ElementsList(
    modifier: Modifier,
    navigateToFarm: () -> Unit,
    navigateToUser: () -> Unit,
    navigateToCattle: () -> Unit,
    navigateToFinance: () -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(5.dp),
        content = {
            item {
                CardItem(
                    "FINCA",
                    R.drawable.ic_farm,
                    onCardSelected = {
                        navigateToFarm()
                    }
                )
            }
            item {
                CardItem(
                    "USUARIO",
                    R.drawable.ic_user,
                    onCardSelected = {
                        navigateToUser()
                    }
                )
            }
            item {
                CardItem(
                    "GANADO",
                    R.drawable.cow,
                    onCardSelected = {
                        navigateToCattle()
                    }
                )
            }
            item {
                CardItem(
                    "FINANZAS",
                    R.drawable.ic_money,
                    onCardSelected = {
                        navigateToFinance()
                    }
                )
            }
        }
    )
}