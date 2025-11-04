package com.example.app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
/*
import com.example.app.viewmodel.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(
    navController: NavController,   // ✅ cambiamos onBack por navController
    vm: WalletViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var to by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var concept by remember { mutableStateOf("") }
    val token = "TOKEN_DE_PRUEBA"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Enviar Dinero") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {   // ✅ botón de volver
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
            OutlinedTextField(
                value = to,
                onValueChange = { to = it },
                label = { Text("Destinatario") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = concept,
                onValueChange = { concept = it },
                label = { Text("Concepto (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    amount.toDoubleOrNull()?.let {
                        vm.send(token, to.trim(), it, concept.ifBlank { null })
                        navController.navigateUp()   // ✅ volver atrás después de enviar
                    }
                },
                enabled = to.isNotBlank() && amount.toDoubleOrNull() != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar envío")
            }
        }
    }
}
*/