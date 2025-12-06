package com.example.app.view

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.model.Usuario
import com.example.app.model.UsuarioDTO
import com.example.app.model.UsuarioLogin
import com.example.app.ui.theme.* // ✅ importa tu paleta de colores
import com.example.app.ui.login.LoginViewModel
import com.example.app.util.DatosUsuario
import com.google.gson.Gson

fun esJsonValido(text: String): Boolean {
    val t = text.trim()
    return t.startsWith("{") && t.endsWith("}")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginResult by viewModel.loginResult.collectAsState()
    val error by viewModel.error.collectAsState()


    LaunchedEffect(loginResult) {
        val usuario = loginResult ?: return@LaunchedEffect

        // Si quieres guardar el JSON REAL
        val userJson = Gson().toJson(usuario)

        if (esJsonValido(userJson)) {
            DatosUsuario.email = usuario.email
            DatosUsuario.nombre = usuario.nombre

            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            Toast.makeText(context, "Respuesta inválida del servidor", Toast.LENGTH_LONG).show()
        }
    }



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
                    containerColor = OrangePrimary
                )
            ) {
                Text("Ingresar", color = Color.White)
            }

            Spacer(Modifier.height(12.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    "¿No tienes cuenta? Regístrate aquí",
                    color = BlueInfo
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
