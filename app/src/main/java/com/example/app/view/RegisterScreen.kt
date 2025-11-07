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
import com.example.app.model.Usuario
import com.example.app.ui.login.LoginViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController,viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current

    // --- Variables de estado ---
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var direccion by remember {mutableStateOf("")}
    var telefono by remember {mutableStateOf("")}

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
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                painter = painterResource(id = R.drawable.logodigipymes),
                contentDescription = "Logo PYMES 360",
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

            Spacer(Modifier.height(12.dp))

            // --DIRECCIÓN -- //
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección completa (Calle, N°, Comuna y región)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // --- TELÉFONO ---
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono de contacto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Telefono") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))

            // --- BOTÓN REGISTRO ---
            Button(
                onClick = {

                    if (name.isNotBlank() && email.isNotBlank() &&
                        password.isNotBlank() && telefono.isNotBlank()
                    ) {
                        val usuario = Usuario(
                            nombre = name,
                            email = email,
                            password = password,
                            direccion = direccion,
                            telefono = telefono,
                            rol = "cliente" // valor por defecto
                        )
                        saveUserData(context, usuario)

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Registro exitoso",
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            )
                        }
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

// --- Guarda usuario completo en SharedPreferences ---
fun saveUserData(context: Context, usuario: Usuario) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("user_nombre", usuario.nombre)
        putString("user_email", usuario.email)
        putString("user_password", usuario.password)
        putString("user_telefono", usuario.direccion)
        putString("user_telefono", usuario.telefono)
        putString("user_rol", usuario.rol) // futuras funciones
        apply()
    }
}



