package com.example.app.view

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.app.viewmodel.PagoViewModel

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(
    navController: NavHostController,
    usuarioId: Long,
    monto: Double,
    viewModel: PagoViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState(initial = viewModel.uiState.value).value
    var descripcion by remember { mutableStateOf("Compra en DigiPyme360") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Confirmar Pago") }
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

            // ============================
            // SI TENEMOS URL → WEBVIEW
            // ============================
            if (!uiState.urlWebPay.isNullOrBlank()) {

                Text(
                    text = "Procesando tu pago...",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        WebView(context).apply {

                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true

                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)

                                    // Detecta la URL de éxito
                                    if (url?.contains("success") == true) {
                                        navController.navigate("compra_exitosa") {
                                            popUpTo("carro_compras") { inclusive = true }
                                        }
                                    }
                                }
                            }

                            loadUrl(uiState.urlWebPay!!)
                        }
                    }
                )

                return@Column
            }

            // ============================
            // MONTO
            // ============================
            Text(
                text = "Total a pagar:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "$$monto",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text(text = "Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Creando preferencia…")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

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
                Text(text = "Confirmar Pago")
            }

            Spacer(modifier = Modifier.height(20.dp))

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
        }
    }
}
