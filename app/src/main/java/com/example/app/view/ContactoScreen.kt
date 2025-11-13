package com.example.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ContactoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Contáctanos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Dirección: Av. Los Emprendedores 123, Santiago")
        Text(text = "Teléfono: +56 9 1234 5678")
        Text(text = "Correo: soporte@pymes360.cl")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Atendemos de lunes a viernes de 9:00 a 18:00 horas.")
    }
}
