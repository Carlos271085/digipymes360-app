package com.example.app.ui.pago

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.viewmodel.PagoViewModel

@Composable
fun PagoScreen(
    usuarioId: Long,
    viewModel: PagoViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.collectAsState().value

    // Si ya tenemos una URL → mostramos el WebView
    if (state.urlWebPay != null) {
        WebViewScreen(state.urlWebPay!!)
        return
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Text("Pago con MercadoPago (Sandbox)", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.pagar(
                    monto = 1000.0,
                    descripcion = "Suscripción usuario",
                    usuarioId = usuarioId
                )
            }
        ) {
            Text("Pagar ahora (sandbox)")
        }

        if (state.isLoading) {
            Spacer(Modifier.height(20.dp))
            CircularProgressIndicator()
        }

        if (state.mensaje != null) {
            Spacer(Modifier.height(20.dp))
            Text(state.mensaje!!)
        }
    }
}
