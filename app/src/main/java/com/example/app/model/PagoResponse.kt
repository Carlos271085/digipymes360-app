package com.example.app.model

data class PagoResponse(
    val status: String,
    val codigoTransaccion: String,
    val mensaje: String?
)
