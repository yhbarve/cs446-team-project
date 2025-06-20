package com.example.cs446_fit4me

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.cs446_fit4me.ui.theme.CS446fit4meTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CS446fit4meTheme {
                var loggedIn by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (loggedIn) {
                        Text("Login successful! Welcome to Fit4Me 🎉", modifier = Modifier.fillMaxSize())
                    } else {
                        LoginScreen(onLoginSuccess = { loggedIn = true })
                    }
                }
            }
        }
    }
}
