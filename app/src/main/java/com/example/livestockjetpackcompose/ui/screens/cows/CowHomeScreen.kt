package com.example.livestockjetpackcompose.ui.screens.cows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.ui.utils.CardItem
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun CowHomeScreen(
    modifier: Modifier,
    userKey: String,
    farmKey: String,
    navigateToBreeding: () -> Unit,
    navigateToLifting: () -> Unit,
    navigateToCorral: () -> Unit,
    navigateToDeadCow: () -> Unit,
    navigateToSoldCow: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(PaddingValues(start = 10.dp, end = 10.dp, bottom = 45.dp, top = 5.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Módulo de Ganado")
        ElementsListCowHome(
            modifier = Modifier.weight(5f),
            navigateToBreeding = { navigateToBreeding() },
            navigateToLifting = { navigateToLifting() },
            navigateToCorral = { navigateToCorral() },
            navigateToDeadCow = { navigateToDeadCow() },
            navigateToSoldCow = { navigateToSoldCow() }
        )
    }
}

@Composable
private fun ElementsListCowHome(
    modifier: Modifier,
    navigateToBreeding: () -> Unit,
    navigateToLifting: () -> Unit,
    navigateToCorral: () -> Unit,
    navigateToDeadCow: () -> Unit,
    navigateToSoldCow: () -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(5.dp),
        content = {
            item {
                CardItem("LEVANTE", R.drawable.ic_lifting, onCardSelected = {
                    navigateToLifting()
                })
            }
            item {
                CardItem("CRIA", R.drawable.ic_breeding, onCardSelected = {
                    navigateToBreeding()
                })
            }
            item {
                CardItem("CORRAL", R.drawable.ic_corral, onCardSelected = {
                    navigateToCorral()
                })
            }
            item {
                CardItem("FALLECIDO", R.drawable.ic_dead_cow, onCardSelected = {
                    navigateToDeadCow()
                })
            }
            item {
                CardItem("VENDIDO", R.drawable.ic_money, onCardSelected = {
                    navigateToSoldCow()
                })
            }
        }
    )
}
