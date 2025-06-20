package com.example.cs446_fit4me

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isSignUpMode by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()

    fun login() {
        isLoading = true
        error = null
        auth.signInWithEmailAndPassword(email.trim(), password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onLoginSuccess()
                } else {
                    error = task.exception?.localizedMessage ?: "Login failed"
                }
            }
    }

    fun signUp() {
        isLoading = true
        error = null
        auth.createUserWithEmailAndPassword(email.trim(), password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onLoginSuccess()
                } else {
                    error = task.exception?.localizedMessage ?: "Signup failed"
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            if (isSignUpMode) "Sign Up" else "Login",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (isSignUpMode) signUp() else login()
            },
            enabled = !isLoading
        ) {
            Text(
                if (isLoading) "Processing..." else if (isSignUpMode) "Sign Up" else "Login"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = {
                isSignUpMode = !isSignUpMode
                error = null
            }
        ) {
            Text(
                if (isSignUpMode) "Already have an account? Log in"
                else "Don't have an account? Sign up"
            )
        }
        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
