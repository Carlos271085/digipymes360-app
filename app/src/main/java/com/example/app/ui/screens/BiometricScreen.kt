package com.example.app.ui.screens

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

@Composable
fun BiometricScreen(
    activity: FragmentActivity,
    navController: NavController
) {
    val context = LocalContext.current
    val executor: Executor = ContextCompat.getMainExecutor(context)
    var message by remember { mutableStateOf("Presiona el botón para autenticarte") }

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Inicio de sesión con huella")
        .setSubtitle("Usa tu huella digital para ingresar")
        .setNegativeButtonText("Cancelar")
        .build()

    val biometricPrompt = remember {
        BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    message = "Autenticación exitosa"
                    Toast.makeText(context, "Bienvenido(a)", Toast.LENGTH_SHORT).show()

                    // ⭐️ NAVEGAR A LOGIN
                    navController.navigate("login") {
                        popUpTo("biometric") { inclusive = true }
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    message = "Error: $errString"
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    message = "Huella no reconocida"
                }
            })
    }

    val biometricManager = BiometricManager.from(context)
    val isAvailable =
        biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text("Autenticación Biométrica", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (isAvailable) {
                        biometricPrompt.authenticate(promptInfo)
                    } else {
                        Toast.makeText(context, "Biometría no disponible o no configurada", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Ingresar con huella")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(message)
        }
    }
}