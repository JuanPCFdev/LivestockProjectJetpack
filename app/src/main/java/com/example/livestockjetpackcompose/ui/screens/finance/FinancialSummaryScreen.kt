package com.example.livestockjetpackcompose.ui.screens.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun FinancialSummaryScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(vertical = 45.dp, horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(Modifier.weight(1f), "Nombre de la Finca")
        CardInfoFarm(Modifier.weight(4f))
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun CardInfoFarm(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "Total invertido",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold
            )
            Text("0000", modifier = Modifier.padding(5.dp))
            Text(
                "Total ganado",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold
            )
            Text("0000", modifier = Modifier.padding(5.dp))
            Text(
                "Total gastado en vacunas",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold
            )
            Text("0000", modifier = Modifier.padding(5.dp))
            Text(
                "Resumen",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold
            )
            Text("0000", modifier = Modifier.padding(5.dp))
        }
    }
}