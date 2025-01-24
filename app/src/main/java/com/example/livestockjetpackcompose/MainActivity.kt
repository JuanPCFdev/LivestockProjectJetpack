package com.example.livestockjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.livestockjetpackcompose.ui.screens.login.LoginScreen
import com.example.livestockjetpackcompose.ui.screens.user.EditUserScreen
import com.example.livestockjetpackcompose.ui.screens.user.RegisterUserScreen
import com.example.livestockjetpackcompose.ui.screens.user.UserInformation
import com.example.livestockjetpackcompose.ui.theme.LivestockJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LivestockJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}