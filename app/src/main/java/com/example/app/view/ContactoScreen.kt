package com.example.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
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
            .padding(24.dp)
    ) {

        // Título principal
        Text(
            text = "Contáctanos",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Tarjeta de datos de contacto ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                // Dirección
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Dirección")
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Dirección", style = MaterialTheme.typography.titleSmall)
                        Text("Av. Los Emprendedores 123, Santiago")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Teléfono
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Teléfono", style = MaterialTheme.typography.titleSmall)
                        Text("+56 9 1234 5678")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Correo
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = "Correo")
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Correo", style = MaterialTheme.typography.titleSmall)
                        Text("soporte@pymes360.cl")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Horarios ---
        Text(
            text = "Horario de atención",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Lunes a viernes de 9:00 a 18:00 horas.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
