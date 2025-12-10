package com.example.app.view

import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.model.Usuario
import com.example.app.model.UsuarioRegistro
import com.example.app.ui.login.LoginViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // --- Variables de estado ---
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // --- Snackbar setup ---
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // --- Scroll ---
    val scrollState = rememberScrollState()
    val registerResult by viewModel.loginResult.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(registerResult) {
        if (registerResult != null) {
            registerResult?.let { usuario ->
                val userJson = Uri.encode(Gson().toJson(usuario))
                navController.navigate("home/$userJson") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

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
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- LOGO ---
            Image(
                painter = painterResource(id = R.drawable.logo2),
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
                label = { Text("Contraseña (mínimo 6 caracteres)") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección completa (Calle, N°, Comuna y Región)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // --- TELÉFONO con prefijo fijo +56 9 ---
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = "+56 9",
                    onValueChange = {},
                    label = { Text("País") },
                    enabled = false,
                    modifier = Modifier.width(100.dp)
                )
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Número de celular") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            // --- BOTÓN REGISTRO ---
            Button(
                onClick = {
                    // VALIDACIONES
                    when {
                        name.isBlank() -> mostrarError(scope, snackbarHostState, "El nombre es obligatorio")
                        !email.contains("@") || !email.contains(".") -> mostrarError(scope, snackbarHostState, "Correo inválido")
                        password.length < 6 -> mostrarError(scope, snackbarHostState, "La contraseña debe tener al menos 6 caracteres")
                        direccion.isBlank() -> mostrarError(scope, snackbarHostState, "La dirección es obligatoria")
                        telefono.length < 8 || telefono.any { !it.isDigit() } ->
                            mostrarError(scope, snackbarHostState, "El número de celular debe tener al menos 8 dígitos numéricos")
                        else -> {
                            val usuario = Usuario(
                                nombre = name,
                                email = email,
                                password = password,
                                direccion = direccion,
                                telefono = "+56 9$telefono",
                                rol = "cliente"
                            )
                            saveUserData(context, usuario)

                            val usuario_a_registrar : UsuarioRegistro

                            usuario_a_registrar = UsuarioRegistro(
                                nombre = name,
                                email = email,
                                password = password
                            )
                            viewModel.registrar(usuario_a_registrar,direccion,telefono)


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

// --- Función para mostrar errores ---
fun mostrarError(scope: CoroutineScope, snackbarHostState: SnackbarHostState, mensaje: String) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = mensaje,
            withDismissAction = true
        )
    }
}

// --- Guarda usuario completo en SharedPreferences ---
fun saveUserData(context: Context, usuario: Usuario) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)


    with(sharedPref.edit()) {
        putString("user_nombre", usuario.nombre)
        putString("user_email", usuario.email)
        putString("user_password", usuario.password)
        putString("user_direccion", usuario.direccion)
        putString("user_telefono", usuario.telefono)
        putString("user_rol", usuario.rol)
        apply()


    }



}
