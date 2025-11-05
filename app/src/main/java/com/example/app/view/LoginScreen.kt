package com.example.app.view

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.app.R
import androidx.compose.ui.graphics.Color
import com.example.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val canUseBiometrics = remember { canAuthenticateWithBiometrics(context) }

    Scaffold(
        // --- BARRA SUPERIOR CON TU COLOR SECUNDARIO (BlueDark) ---
        topBar = {
            TopAppBar(
                title = { Text("Iniciar Sesión", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueDark, // azul petróleo de tu paleta
                    titleContentColor = Color.White
                )
            )
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- LOGO CENTRADO ---
            Image(
                painter = painterResource(id = R.drawable.logodigipymes),
                contentDescription = "Logo Pymes 360",
                modifier = Modifier
                    .size(110.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Bienvenido a PYMES 360",
                style = MaterialTheme.typography.headlineSmall,
                color = OrangePrimary // Naranja principal de tu paleta
            )

            Spacer(Modifier.height(24.dp))

            // --- CAMPOS DE TEXTO ---
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary, // color al enfocar
                    unfocusedBorderColor = BlueDark,
                    cursorColor = OrangePrimary
                )
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = TextSecondary) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    unfocusedBorderColor = BlueDark,
                    cursorColor = OrangePrimary
                )
            )

            Spacer(Modifier.height(16.dp))

            // --- BOTÓN LOGIN ---
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        navController.navigate("home")
                    }
                },
                modifier = Modifier.wrapContentWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangePrimary, // color principal
                    contentColor = Color.White
                )
            ) {
                Text("Ingresar")
            }

            Spacer(Modifier.height(12.dp))

            // --- BOTÓN REGISTRO ---
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    "¿No tienes cuenta? Regístrate aquí",
                    color = BlueInfo // Azul informativo de tu paleta
                )
            }

            Spacer(Modifier.height(24.dp))

            // --- HUELLA DACTILAR ---
            if (canUseBiometrics) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val activity = context as? androidx.fragment.app.FragmentActivity
                    IconButton(
                        onClick = {
                            activity?.let {
                                authenticateWithBiometrics(it) { navController.navigate("home") }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.huella),
                            contentDescription = "Huella dactilar",
                            tint = BlueDark,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Text(
                        text = "Presiona para iniciar sesión con tu huella dactilar",
                        style = MaterialTheme.typography.bodySmall,
                        color = BlueDark
                    )
                }
            }
        }
    }
}

// --- Funciones biométricas (SIN CAMBIOS) ---

fun canAuthenticateWithBiometrics(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.DEVICE_CREDENTIAL

    return biometricManager.canAuthenticate(authenticators) ==
            BiometricManager.BIOMETRIC_SUCCESS
}

fun authenticateWithBiometrics(activity: androidx.fragment.app.FragmentActivity, onSuccess: () -> Unit) {
    val executor = ContextCompat.getMainExecutor(activity)
    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Autenticación biométrica")
        .setSubtitle("Usa tu huella o PIN para iniciar sesión")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        .build()

    biometricPrompt.authenticate(promptInfo)
}
