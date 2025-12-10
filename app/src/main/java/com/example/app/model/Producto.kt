package com.example.app.model

// --- Modelo de producto ---
data class Producto(
    val id: Int,
    val idNegocio: Int,
    var stock: Int,
    val nombre: String,
    val precio: String,
    val descripcion: String

)
