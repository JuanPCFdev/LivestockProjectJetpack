package com.example.livestockjetpackcompose.ui.screens.cows

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
fun CowResumeScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Hoja de vida de la vaca xxxxxxx")
        InfoCardCow(Modifier.weight(4f))
        ConsultVaccineCowButton(Modifier.weight(1f))
    }
}

@Composable
private fun InfoCardCow(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Text("Información de la vaca", modifier = Modifier.padding(5.dp))
    }
}

@Composable
private fun ConsultVaccineCowButton(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Consultar Vacunas"){

        }
    }
}