package com.example.app.model

data class PagoRequest(
    val monto: Double,
    val descripcion: String,
    val usuarioId: Long
)
