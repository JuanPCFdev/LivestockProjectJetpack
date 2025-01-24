package com.example.livestockjetpackcompose.ui.screens.list_screens

import androidx.compose.foundation.background
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
import com.example.livestockjetpackcompose.domain.model.User
import com.example.livestockjetpackcompose.ui.utils.ButtonCustom
import com.example.livestockjetpackcompose.ui.utils.ButtonType
import com.example.livestockjetpackcompose.ui.utils.Title
import com.example.livestockjetpackcompose.ui.theme.background_app
import kotlinx.serialization.json.Json

@Composable
fun ListFarmScreen(
    modifier: Modifier,
    serializedUser: String,
    navigateToScreen: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = background_app)
            .padding(vertical = 45.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val user = Json.decodeFromString<User>(serializedUser)

        Title(Modifier.weight(1f), "Bienvenido ${user.name}!")
        BodyItemsList(Modifier.weight(6f))
        RegisterFarmButton(Modifier.weight(1f)) {
            navigateToScreen()
        }
    }
}

@Composable
private fun BodyItemsList(modifier: Modifier) {
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
            item {
                Text("item 7")
            }
            item {
                Text("item 8")
            }
            item {
                Text("item 9")
            }
        }
    }
}

@Composable
private fun RegisterFarmButton(modifier: Modifier, navigateToScreen: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL, "Registrar Finca") {
            navigateToScreen()
        }
    }
}