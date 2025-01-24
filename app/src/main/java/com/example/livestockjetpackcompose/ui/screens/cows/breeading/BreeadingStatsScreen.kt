package com.example.livestockjetpackcompose.ui.screens.cows.breeading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun BreeadingStatsScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Estadísticas Vaca 112233")
        CardBirthList(Modifier.weight(2f))
        StatsBreeadingCard(Modifier.weight(3f))
        RegisterNewsButton(Modifier.weight(1f))
    }
}

@Composable
private fun CardBirthList(modifier: Modifier) {
    Card(modifier = modifier
        .fillMaxSize()
        .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            item {
                Text("item 0")
            }
            item {
                Text("item 1")
            }
            item {
                Text("item 2")
            }
            item {
                Text("item 3")
            }
            item {
                Text("item 4")
            }
            item {
                Text("item 5")
            }
            item {
                Text("item 6")
            }
        }
    }
}

@Composable
private fun StatsBreeadingCard(modifier: Modifier) {
    Card(modifier = modifier
        .fillMaxSize()
        .padding(vertical = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("Estadisticas hijos aekljrhaskjdhaskjd", modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
private fun RegisterNewsButton(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Novedad"){

        }
    }
}