package com.example.app.model

data class Usuario(
    val id_usuario: Long? = null,   // ID opcional del usuario
    val nombre: String,             // Nombre completo del usuario
    val email: String,              // Correo electrónico
    val password: String,           //
    val direccion: String,
    val telefono: String,           // Teléfono de contacto
    val rol: String                 // Rol (ej: cliente, pyme, admin)
)
