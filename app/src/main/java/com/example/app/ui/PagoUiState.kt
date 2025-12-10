package com.example.app.ui

data class PagoUiState(
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val exito: Boolean? = null,
    val urlWebPay: String? = null
)
