package com.example.app.view

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // --- Snackbar setup ---
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) } // ✅ Muestra snackbar
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- LOGO ---
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Pymes 360",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Crea tu cuenta PYMES 360",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(24.dp))

            // --- CAMPOS DEL FORMULARIO ---
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // --- BOTÓN REGISTRO ---
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                        saveUserData(context, email, password)
                        // Mostrar SnackBar
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Registro exitoso 🎉",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
                        // Navegar después de un pequeño delay
                        scope.launch {
                            kotlinx.coroutines.delay(2000)
                            navController.navigate("login")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Completa todos los campos",
                                withDismissAction = true
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Registrarse", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(Modifier.height(12.dp))

            // --- BOTÓN VOLVER AL LOGIN ---
            TextButton(onClick = { navController.navigate("login") }) {
                Text(
                    "¿Ya tienes cuenta? Inicia sesión aquí",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

// --- Guarda usuario en SharedPreferences ---
fun saveUserData(context: Context, email: String, password: String) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("user_email", email)
        putString("user_password", password)
        apply()
    }
}



