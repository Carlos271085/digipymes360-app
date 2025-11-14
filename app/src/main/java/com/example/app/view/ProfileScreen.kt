package com.example.app.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.example.app.R
import com.example.app.util.getUserAddress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, user: String, email: String) {

    // Variables de estado para manejar dirección y errores
    val context = LocalContext.current
    var direccion by remember { mutableStateOf<String?>(null) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del Usuario") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Título
            Text(
                "Bienvenido a la tienda digital",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen de perfil
            Image(
                painter = painterResource(id = R.drawable.gato_naranja),
                contentDescription = "Logo PYMES 360",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Información de usuario
            Text("Usuario: $user", style = MaterialTheme.typography.bodyLarge)
            Text("Email: $email", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para obtener ubicación actual
            Button(onClick = {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getUserAddress(
                        context = context,
                        onSuccess = { direccionObtenida ->
                            direccion = direccionObtenida
                            mensajeError = null
                        },
                        onError = { error ->
                            mensajeError = error
                        }
                    )
                } else {
                    mensajeError = "Debes otorgar permisos de ubicación"
                }
            }) {
                Text("Obtener dirección actual")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar resultados
            when {
                mensajeError != null -> Text("Error: $mensajeError")
                direccion != null -> Text(" Dirección actual:\n$direccion")
                else -> Text("Presiona el botón para obtener tu dirección")
            }
        }
    }
}
