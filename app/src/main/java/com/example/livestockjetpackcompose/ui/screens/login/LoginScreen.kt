package com.example.livestockjetpackcompose.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livestockjetpackcompose.R
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(modifier: Modifier) {
    var loading by remember { mutableStateOf(false) }

    if (loading) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFFFF))
                .padding(45.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(Modifier.weight(1f))
            Body(Modifier.weight(3f))
            Footer(Modifier.weight(1f))
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        LaunchedEffect(Unit) {
            delay(3000L)
            loading = true
        }
    }
}

@Composable
fun Title(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Welcome",
            fontSize = 30.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = "Sign In to continue",
            fontSize = 30.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}

@Composable
fun Body(modifier: Modifier) {
    //Card
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextFieldUser()
        OutlinedTextFieldPassword()
        Buttons()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldPassword() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        label = { Text("Password") },
        placeholder = { Text("Password") },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "") },
        prefix = { Text("Password : ") },
        keyboardActions = KeyboardActions(onSearch = {}),
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        shape = RoundedCornerShape(10.dp),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color(0xFF81cdf7),
            focusedBorderColor = Color(0xFF81cdf7)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldUser() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        label = { Text("User") },
        placeholder = { Text("User") },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "") },
        prefix = { Text("User : ") },
        keyboardActions = KeyboardActions(onSearch = {}),
        singleLine = true,
        maxLines = 1,
        minLines = 1,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color(0xFF81cdf7),
            focusedBorderColor = Color(0xFF81cdf7)
        )
    )
}


@Composable
fun Buttons() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 15.dp)
    ) {
        Button(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            colors = ButtonColors(
                containerColor = Color(0xFF0057ff),
                contentColor = Color.White,
                disabledContentColor = Color.LightGray,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(10.dp)
        ) {
            Text("Login", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Button(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            colors = ButtonColors(
                containerColor = Color(0xFFb0d7ff),
                contentColor = Color.Black,
                disabledContentColor = Color.LightGray,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(10.dp)
        ) {
            Text("Register", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            colors = ButtonColors(
                containerColor = Color(0xFFb0d7ff),
                contentColor = Color.Black,
                disabledContentColor = Color.LightGray,
                disabledContainerColor = Color.LightGray
            ),
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(10.dp)
        ) {
            Text("¿How to login?", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@Composable
fun Footer(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.cow),
        modifier = modifier.size(100.dp),
        contentDescription = ""
    )
}