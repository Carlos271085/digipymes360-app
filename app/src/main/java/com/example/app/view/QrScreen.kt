package com.example.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cobrar con QR") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {   // ✅ Botón para volver
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .padding(16.dp)
        ) {
            Text("Aquí se generará el código QR del cobro.")
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigateUp() }) {   // ✅ También vuelve atrás
                Text("Volver")
            }
        }
    }
}


