package com.example.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.app.viewmodel.PagoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(
    navController: NavHostController,
    usuarioId: Long,
    monto: Double,
    viewModel: PagoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var descripcion by remember { mutableStateOf("Compra en DigiPyme360") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmar Pago") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            // -------------------------
            // MONTO TOTAL (NO EDITABLE)
            // -------------------------
            Text(
                text = "Total a pagar:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "$$monto",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // -------------------------
            // DESCRIPCIÓN DEL PAGO
            // -------------------------
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // -------------------------
            // PROCESANDO...
            // -------------------------
            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Procesando pago…")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // -------------------------
            // BOTÓN CONFIRMAR PAGO
            // -------------------------
            Button(
                onClick = {
                    viewModel.pagar(
                        monto = monto,
                        descripcion = descripcion,
                        usuarioId = usuarioId
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Confirmar Pago")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // -------------------------
            // MENSAJE DE RESULTADO
            // -------------------------
            uiState.mensaje?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = if (uiState.exito == true)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // -------------------------
            // CÓDIGO DE TRANSACCIÓN
            // -------------------------
            uiState.codigoTransaccion?.let { codigo ->
                Text(
                    text = "Código de transacción: $codigo",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // -------------------------
            // NAVEGAR CUANDO EL PAGO ES EXITOSO
            // -------------------------
            if (uiState.exito == true) {
                LaunchedEffect(Unit) {
                    navController.navigate("compra_exitosa") {
                        popUpTo("carro_compras") { inclusive = true }
                    }
                }
            }
        }
    }
}
