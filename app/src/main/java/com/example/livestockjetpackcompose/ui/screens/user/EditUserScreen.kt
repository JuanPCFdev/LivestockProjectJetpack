package com.example.livestockjetpackcompose.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livestockjetpackcompose.ui.ButtonCustom
import com.example.livestockjetpackcompose.ui.ButtonType
import com.example.livestockjetpackcompose.ui.Footer
import com.example.livestockjetpackcompose.ui.Title
import com.example.livestockjetpackcompose.ui.theme.background_app

@Composable
fun EditUserScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background_app)
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(Modifier.weight(1f), "Example User Name")
        Body(Modifier.weight(2f))
        EditButton(Modifier.weight(1f))
        Footer(Modifier.weight(1f))
    }
}

@Composable
private fun Body(modifier: Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Slot("Name", "User123")
        Slot("Phone", "123456789")
    }
}

@Composable
private fun Slot(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(5.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = content,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
private fun EditButton(modifier: Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        ButtonCustom(ButtonType.SPECIAL,"Edit")
    }
}