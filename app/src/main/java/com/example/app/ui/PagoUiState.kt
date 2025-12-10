package com.example.app.ui.state

data class PagoUiState(
    val isLoading: Boolean = false,
    val exito: Boolean = false,
    val mensaje: String? = null,
    val urlWebPay: String? = null,
    val publicKey: String? = null
)