package com.example.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ContactoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Contáctanos", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Dirección: Av. Los Emprendedores 123, Santiago")
        Text("Teléfono: +56 9 1234 5678")
        Text("Correo: soporte@pymes360.cl")
        Spacer(Modifier.height(16.dp))
        Text("Atendemos de lunes a viernes de 9:00 a 18:00 horas.")
    }
}