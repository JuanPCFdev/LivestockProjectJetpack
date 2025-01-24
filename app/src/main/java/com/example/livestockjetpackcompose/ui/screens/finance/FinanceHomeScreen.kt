package com.example.livestockjetpackcompose.ui.screens.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.livestockjetpackcompose.R
import com.example.livestockjetpackcompose.ui.utils.CardItem
import com.example.livestockjetpackcompose.ui.utils.Footer
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun FinanceHomeScreen(modifier: Modifier, userKey: String, farmKey: String) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Finanzas")
        OptionListFinance(Modifier.weight(5f))
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun OptionListFinance(modifier: Modifier) {
    LazyColumn(modifier = modifier.padding(5.dp),
        content = {
            item {
                CardItem("Ganancias y perdidas", R.drawable.ic_profits_losses, onCardSelected = {})
            }
            item {
                CardItem("Historial de Recibos", R.drawable.ic_receipt_history, onCardSelected = {})
            }
            item {
                CardItem("Gastos", R.drawable.ic_expense, onCardSelected = {})
            }
            item {
                CardItem("Vender Ganado", R.drawable.ic_corral, onCardSelected = {})
            }
        }
    )
}
