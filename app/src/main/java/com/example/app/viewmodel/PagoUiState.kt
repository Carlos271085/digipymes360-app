package com.example.app.viewmodel

data class PagoUiState(
    val isLoading: Boolean = false,
    val exito: Boolean = false,
    val mensaje: String? = null,
    val urlWebPay: String? = null,
    val publicKey: String? = null,
    val pagoCompletado: Boolean = false,
    val pagoFallido: Boolean = false,
    val pagoPendiente: Boolean = false
)

