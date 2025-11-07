package com.example.app.view

import android.content.Context
import android.net.Uri
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
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.app.R
import androidx.compose.ui.graphics.Color
import com.example.app.ui.theme.*
import com.example.app.ui.login.LoginViewModel
import com.google.gson.Gson
import android.widget.Toast
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val activity = context as FragmentActivity // ✅ necesario para el BiometricPrompt

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult by viewModel.loginResult.collectAsState()
    val error by viewModel.error.collectAsState()

    // Si el login fue exitoso
    LaunchedEffect(loginResult) {
        if (loginResult != null) {
            loginResult?.let { usuario ->
                val userJson = Uri.encode(Gson().toJson(usuario))
                navController.navigate("home/$userJson") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    // Si hubo error
    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Iniciar Sesión") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
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
            Image(
                painter = painterResource(id = R.drawable.logodigipymes),
                contentDescription = "Logo Pymes 360",
                modifier = Modifier
                    .size(110.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Bienvenido(a) a DIGIPYMES360",
                style = MaterialTheme.typography.headlineSmall,
                color = OrangePrimary
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
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

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login(email, password)
                    } else {
                        Toast.makeText(
                            context,
                            "Ingresa email y contraseña",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Ingresar")
            }

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    "¿No tienes cuenta? Regístrate aquí",
                    color = BlueInfo
                )
            }

            Spacer(Modifier.height(24.dp))

            // ✅ BLOQUE DE HUELLA DACTILAR
            val biometricManager = BiometricManager.from(context)
            val canUseBiometrics = biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BiometricManager.BIOMETRIC_SUCCESS

            if (canUseBiometrics) {
                Button(
                    onClick = {
                        val executor = ContextCompat.getMainExecutor(context)
                        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Acceso con huella digital")
                            .setSubtitle("Usa tu huella para ingresar")
                            .setNegativeButtonText("Cancelar")
                            .build()

                        val biometricPrompt = BiometricPrompt(
                            activity,
                            executor,
                            object : BiometricPrompt.AuthenticationCallback() {
                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                    super.onAuthenticationSucceeded(result)
                                    Toast.makeText(
                                        context,
                                        "Huella verificada con éxito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }

                                override fun onAuthenticationError(
                                    errorCode: Int,
                                    errString: CharSequence
                                ) {
                                    super.onAuthenticationError(errorCode, errString)
                                    Toast.makeText(
                                        context,
                                        "Error: $errString",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onAuthenticationFailed() {
                                    super.onAuthenticationFailed()
                                    Toast.makeText(
                                        context,
                                        "Huella no reconocida",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })

                        biometricPrompt.authenticate(promptInfo)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BlueDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.huella),
                        contentDescription = "Huella",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Ingresar con huella", color = Color.White)
                }
            } else {
                Text(
                    "Huella digital no disponible en este dispositivo",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

