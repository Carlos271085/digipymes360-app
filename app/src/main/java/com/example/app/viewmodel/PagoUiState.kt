package com.example.app.viewmodel

data class PagoUiState(
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val exito: Boolean? = null,
    val urlWebPay: String? = null
)