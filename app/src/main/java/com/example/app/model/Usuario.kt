package com.example.app.model


data class Usuario(
    val id_usuario: Long? = null,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String
)
